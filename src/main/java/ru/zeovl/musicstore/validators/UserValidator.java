package ru.zeovl.musicstore.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.zeovl.musicstore.models.User;
import ru.zeovl.musicstore.services.UserService;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (userService.existsByUsername(((User) target).getUsername()))
            errors.rejectValue("username", "", "User already exists");
        if (userService.existsByEmail(((User) target).getEmail()))
            errors.rejectValue("email", "", "Email is already used");
    }
}
