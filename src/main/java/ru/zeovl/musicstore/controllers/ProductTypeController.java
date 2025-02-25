package ru.zeovl.musicstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zeovl.musicstore.services.ProductTypeService;

@Controller
@RequestMapping("/product_types")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @GetMapping("")
    String getProductTypesList(Model model) {
        model.addAttribute("list", productTypeService.findAll());
        return "product_types_list";
    }
}
