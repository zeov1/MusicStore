package ru.zeovl.musicstore.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.zeovl.musicstore.dto.UserDTO;

@Component
public class UserDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!((UserDTO) target).getPassword().equals(((UserDTO) target).getPassword2()))
            errors.rejectValue("password2", "", "Passwords don't match!");
    }
}
