package ru.zeovl.musicstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zeovl.musicstore.services.PhotoService;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("")
    String getPhotosList(Model model) {
        model.addAttribute("list", photoService.findAll());
        return "photos_list";
    }
}
