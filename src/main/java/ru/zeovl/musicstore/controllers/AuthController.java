package ru.zeovl.musicstore.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zeovl.musicstore.dto.UserDTO;
import ru.zeovl.musicstore.models.User;
import ru.zeovl.musicstore.services.RegisterService;
import ru.zeovl.musicstore.validators.UserDTOValidator;
import ru.zeovl.musicstore.validators.UserValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserValidator userValidator;
    private final UserDTOValidator userDTOValidator;
    private final RegisterService registerService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(UserValidator userValidator, UserDTOValidator userDTOValidator, RegisterService registerService, ModelMapper modelMapper) {
        this.userValidator = userValidator;
        this.userDTOValidator = userDTOValidator;
        this.registerService = registerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationPage(@ModelAttribute(name = "userDTO") UserDTO userDTO) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String proceedRegistration(@ModelAttribute(name = "userDTO") UserDTO userDTO, BindingResult bindingResult) {
        userDTOValidator.validate(userDTO, bindingResult);
        User user = convertToUser(userDTO);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
            return "auth/register";
        }
        registerService.register(user);
        return "redirect:/auth/login?registered";
    }

    // DTO converting

    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
