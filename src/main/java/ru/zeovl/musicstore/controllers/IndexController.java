package ru.zeovl.musicstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zeovl.musicstore.services.ManufacturerService;
import ru.zeovl.musicstore.services.PhotoService;
import ru.zeovl.musicstore.services.ProductService;
import ru.zeovl.musicstore.services.ProductTypeService;

@Controller
public class IndexController {

    private final ManufacturerService manufacturerService;
    private final PhotoService photoService;
    private final ProductService productService;
    private final ProductTypeService productTypeService;

    @Autowired
    public IndexController(ManufacturerService manufacturerService, PhotoService photoService, ProductService productService, ProductTypeService productTypeService) {
        this.manufacturerService = manufacturerService;
        this.photoService = photoService;
        this.productService = productService;
        this.productTypeService = productTypeService;
    }

    @GetMapping("/")
    String index() {
        return "index";
    }
}
