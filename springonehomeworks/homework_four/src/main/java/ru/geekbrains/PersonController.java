package ru.geekbrains;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.entity.Person;
import ru.geekbrains.service.PersonService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String allPersons(@RequestParam(value = "minAge") Optional<Integer> minAge,
                             @RequestParam(value = "maxAge") Optional<Integer> maxAge,
                             @RequestParam(value = "page") Optional<Integer> page,
                             @RequestParam(value = "size") Optional<Integer> size,
                             Model model) {
        model.addAttribute("activePage", "Persons");
        model.addAttribute("personPage", personService.findAllByAgeBetween(
                minAge, maxAge,
                PageRequest.of(page.orElse(1) - 1, size.orElse(5))
        ));
        model.addAttribute("minAge", minAge.orElse(null));
        model.addAttribute("maxAge", maxAge.orElse(null));
        return "persons";
    }

    @GetMapping("/form")
    public String formPerson(@RequestParam(value = "id") Optional<Long> personId,
                             Model model) {
        if (personId.isPresent()) {
            model.addAttribute("person", personService.findById(personId.get()));
        } else {
            model.addAttribute("person", new Person());
        }
        return "person_form";
    }

    @PostMapping("/form")
    public String newPerson(@Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "person_form";
        }

        personService.save(person);
        return "redirect:/person";
    }
}
