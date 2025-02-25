package ru.zeovl.musicstore.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zeovl.musicstore.models.Photo;
import ru.zeovl.musicstore.models.Product;
import ru.zeovl.musicstore.services.PhotoService;
import ru.zeovl.musicstore.services.ProductService;

import java.util.List;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    private final PhotoService photoService;
    private final ProductService productService;

    @Autowired
    public PhotoController(PhotoService photoService, ProductService productService) {
        this.photoService = photoService;
        this.productService = productService;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CREATE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("/new")
    String newPhoto(Model model) {
        Photo photo = new Photo();
        model.addAttribute("photo", photo);
        model.addAttribute("isNew", true);
        return "photo/photo_form";
    }

    @PostMapping("")
    String createPhoto(@ModelAttribute @Valid Photo photo, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isNew", true);
            return "photo/photo_form";
        }
        photoService.save(photo);
        return "redirect:/photos";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ READ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("")
    String getPhotosList(Model model) {
        model.addAttribute("list", photoService.findAll());
        return "photo/photos_list";
    }

    @GetMapping("/{id}")
    String getPhotoById(@PathVariable int id, Model model) {
        Photo photo = photoService.findById(id);
        List<Product> relatedProducts = productService.findByPhotosContaining(photo);
        model.addAttribute("photo", photo);
        model.addAttribute("products", relatedProducts);
        return "photo/photo_detail";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ UPDATE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("/{id}/edit")
    String editPhoto(@PathVariable int id, Model model) {
        Photo photo = photoService.findById(id);
        model.addAttribute("photo", photo);
        return "photo/photo_form";
    }

    @PatchMapping("/{id}")
    String updatePhoto(@PathVariable int id, @ModelAttribute @Valid Photo photo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "photo/photo_form";
        }
        photoService.update(id, photo);
        return "redirect:/photos";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DELETE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("/{id}/delete")
    String confirmDeletingPhotoById(@PathVariable int id, Model model) {
        Photo photo = photoService.findById(id);
        model.addAttribute("photo", photo);
        return "photo/photo_delete_confirmation";
    }

    @DeleteMapping("/{id}")
    String deletePhotoById(@PathVariable int id) {
        photoService.deleteById(id);
        return "redirect:/photos";
    }
}
