package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.entity.Person;
import ru.geekbrains.entity.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional(readOnly = true)
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Person> findAll(Pageable pageable) {
       return personRepository.findAll(pageable);
    }

    public Page<Person> findAllByAgeBetween(Optional<Integer> min, Optional<Integer> max, Pageable pageable) {
        if (min.isPresent() && max.isPresent()) {
            return personRepository.findAllByAgeBetween(min.get(), max.get(), pageable);
        }
        if (min.isPresent()) {
            return personRepository.findAllByAgeGreaterThanEqual(min.get(), pageable);
        }
        if (max.isPresent()) {
            return personRepository.findAllByAgeLessThanEqual(max.get(), pageable);
        }
        return personRepository.findAll(pageable);
    }
}
