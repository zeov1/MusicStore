package ru.zeovl.musicstore.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping("/")
    String index() {
        String out = "Hello World";
        return out;
    }
}
