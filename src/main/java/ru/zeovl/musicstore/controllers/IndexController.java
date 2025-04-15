package ru.zeovl.musicstore.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.zeovl.musicstore.security.UserDetailsImpl;

@Controller
public class IndexController {
    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/profile")
    String profile(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("user", userDetails);
        return "profile";
    }
}
