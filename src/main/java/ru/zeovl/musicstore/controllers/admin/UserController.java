package ru.zeovl.musicstore.controllers.admin;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.zeovl.musicstore.dto.UserDTO;
import ru.zeovl.musicstore.exceptions.InvalidUserException;
import ru.zeovl.musicstore.exceptions.UserNotFoundException;
import ru.zeovl.musicstore.models.User;
import ru.zeovl.musicstore.services.UserService;
import ru.zeovl.musicstore.validators.UserDTOValidator;
import ru.zeovl.musicstore.validators.UserValidator;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserValidator userValidator;
    private final UserDTOValidator userDTOValidator;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, UserValidator userValidator, UserDTOValidator userDTOValidator) {
        this.userValidator = userValidator;
        this.userDTOValidator = userDTOValidator;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    // Create

    @GetMapping("/new")
    String newUser(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("isNew", true);
        return "admin/user/user_form";
    }

    @PostMapping("")
    String createUser(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
        userDTOValidator.validate(userDTO, bindingResult);
        User user = convertToUser(userDTO);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("isNew", true);
            return "admin/user/user_form";
        }
        userService.save(user);
        return "redirect:/admin/users";
    }

    // Read

    @GetMapping("")
    String getUsersList(Model model) {
        model.addAttribute("list", userService.findAllOrderedById());
        return "admin/user/users_list";
    }

    @GetMapping("/{id}")
    String getUserById(@PathVariable long id, Model model) {
        try {
            User user = userService.findById(id);
            model.addAttribute("user", user);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "admin/user/user_detail";
    }

    // Update

    @GetMapping("/{id}/edit")
    String editUser(@PathVariable long id, Model model) {
        try {
            User user = userService.findById(id);
            UserDTO userDTO = convertToDTO(user);
            model.addAttribute("userDTO", userDTO);
            model.addAttribute("id", user.getId());
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "admin/user/user_form";
    }

    @PatchMapping("/{id}")
    String updateUser(@PathVariable long id, @ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult) {
        User user = convertToUser(userDTO);
        try {
            userService.update(id, user, bindingResult);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (InvalidUserException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return "redirect:/admin/users";
    }

    // Delete

    @GetMapping("/{id}/delete")
    String confirmDeletingUser(@PathVariable long id, Model model) {
        try {
            User user = userService.findById(id);
            model.addAttribute("user", user);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "admin/user/user_delete_confirmation";
    }

    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    // DTO converting

    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

}
