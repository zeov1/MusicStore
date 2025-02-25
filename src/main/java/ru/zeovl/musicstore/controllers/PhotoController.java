package ru.zeovl.musicstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("")
    String getPhotosList(Model model) {
        model.addAttribute("list", photoService.findAll());
        return "photos_list";
    }

    @GetMapping("/{id}")
    String getPhotoById(@PathVariable int id, Model model) {
        Photo photo = photoService.findById(id);
        List<Product> relatedProducts = productService.findByPhotosContaining(photo);
        model.addAttribute("photo", photo);
        model.addAttribute("products", relatedProducts);
        return "photo_detail";
    }
}
