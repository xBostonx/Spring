package ru.geekbrains;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("welcomeText", "Welcome to our shop!");
        return "start";
    }
}
