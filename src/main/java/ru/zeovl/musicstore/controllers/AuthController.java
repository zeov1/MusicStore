package ru.zeovl.musicstore.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zeovl.musicstore.models.User;
import ru.zeovl.musicstore.services.RegisterService;
import ru.zeovl.musicstore.validators.UserValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserValidator userValidator;
    private final RegisterService registerService;

    @Autowired
    public AuthController(UserValidator userValidator, RegisterService registerService) {
        this.userValidator = userValidator;
        this.registerService = registerService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationPage(@ModelAttribute(name = "user") User user) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String proceedRegistration(@ModelAttribute(name = "user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
            return "auth/register";
        }
        registerService.register(user);
        return "redirect:/auth/login?registered";
    }
}
