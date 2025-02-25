package ru.zeovl.musicstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zeovl.musicstore.models.Product;
import ru.zeovl.musicstore.models.ProductType;
import ru.zeovl.musicstore.services.ProductService;
import ru.zeovl.musicstore.services.ProductTypeService;

import java.util.List;

@Controller
@RequestMapping("/product_types")
public class ProductTypeController {

    private final ProductTypeService productTypeService;
    private final ProductService productService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService, ProductService productService) {
        this.productTypeService = productTypeService;
        this.productService = productService;
    }

    @GetMapping("")
    String getProductTypesList(Model model) {
        model.addAttribute("list", productTypeService.findAll());
        return "product_types_list";
    }

    @GetMapping("/{id}")
    String getProductTypeById(@PathVariable int id, Model model) {
        ProductType productType = productTypeService.findById(id);
        List<Product> relatedProducts = productService.findByProductType(productType);
        model.addAttribute("productType", productType);
        model.addAttribute("products", relatedProducts);
        return "product_type_detail";
    }
}
