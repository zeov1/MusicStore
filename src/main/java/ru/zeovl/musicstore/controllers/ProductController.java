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
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final PhotoService photoService;

    @Autowired
    public ProductController(ProductService productService, PhotoService photoService) {
        this.productService = productService;
        this.photoService = photoService;
    }

    @GetMapping("")
    String getProductsList(Model model) {
        model.addAttribute("list", productService.findAll());
        return "products_list";
    }

    @GetMapping("/{id}")
    String getProductById(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        List<Photo> relatedPhotos = photoService.getByProductsContaining(product);
        model.addAttribute("product", product);
        model.addAttribute("photos", relatedPhotos);
        return "product_detail";
    }
}
