package ru.zeovl.musicstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zeovl.musicstore.models.Photo;
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

    @GetMapping("/new")
    String newProductType(Model model) {
        ProductType productType = new ProductType();
        model.addAttribute("productType", productType);
        return "product_type/product_type_form";
    }

    @PostMapping("")
    String createProductType(@ModelAttribute ProductType productType) {
        productTypeService.save(productType);
        return "redirect:/product_types";
    }

    @GetMapping("")
    String getProductTypesList(Model model) {
        model.addAttribute("list", productTypeService.findAll());
        return "product_type/product_types_list";
    }

    @GetMapping("/{id}")
    String getProductTypeById(@PathVariable int id, Model model) {
        ProductType productType = productTypeService.findById(id);
        List<Product> relatedProducts = productService.findByProductType(productType);
        model.addAttribute("productType", productType);
        model.addAttribute("products", relatedProducts);
        return "product_type/product_type_detail";
    }

    @GetMapping("/{id}/edit")
    String editProductType(@PathVariable int id, Model model) {
        ProductType productType = productTypeService.findById(id);
        model.addAttribute("productType", productType);
        return "product_type/product_type_form";
    }

    @PatchMapping("/{id}")
    String updateProductType(@PathVariable int id, ProductType productType) {
        productTypeService.update(id, productType);
        return "redirect:/product_types";
    }

    @GetMapping("/{id}/delete")
    String confirmDeletingProductTypeById(@PathVariable int id, Model model) {
        ProductType productType = productTypeService.findById(id);
        model.addAttribute("productType", productType);
        return "product_type/product_type_delete_confirmation";
    }

    @DeleteMapping("/{id}")
    String deleteProductTypeById(@PathVariable int id) {
        productTypeService.deleteById(id);
        return "redirect:/product_types";
    }
}
