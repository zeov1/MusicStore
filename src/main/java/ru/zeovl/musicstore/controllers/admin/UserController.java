package ru.zeovl.musicstore.controllers.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zeovl.musicstore.models.User;
import ru.zeovl.musicstore.services.UserService;

// TODO implement UserDTO!!!

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CREATE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("/new")
    String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("isNew", true);
        return "admin/user/user_form";
    }

    @PostMapping("")
    String createUser(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
        // TODO check if present
        if (bindingResult.hasErrors()) {
            model.addAttribute("isNew", true);
            return "admin/user/user_form";
        }
        userService.save(user);
        return "redirect:/admin/users";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ READ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("")
    String getUsersList(Model model) {
        model.addAttribute("list", userService.findAllOrderedById());
        return "admin/user/users_list";
    }

    @GetMapping("/{id}")
    String getUserById(@PathVariable long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/user/user_detail";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ UPDATE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("/{id}/edit")
    String editUser(@PathVariable long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/user/user_form";
    }

    @PatchMapping("/{id}")
    String updateUser(@PathVariable long id, @ModelAttribute @Valid User user, BindingResult bindingResult) {
        // FIXME doesn't work, DTO needed
        if (bindingResult.hasErrors()) {
            return "admin/user/user_form";
        }
        userService.update(id, user);
        return "redirect:/admin/users";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DELETE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("/{id}/delete")
    String confirmDeletingUser(@PathVariable long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/user/user_delete_confirmation";
    }

    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
