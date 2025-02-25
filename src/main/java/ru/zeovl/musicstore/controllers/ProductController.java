package ru.zeovl.musicstore.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zeovl.musicstore.models.Photo;
import ru.zeovl.musicstore.models.Product;
import ru.zeovl.musicstore.services.ManufacturerService;
import ru.zeovl.musicstore.services.PhotoService;
import ru.zeovl.musicstore.services.ProductService;
import ru.zeovl.musicstore.services.ProductTypeService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final PhotoService photoService;
    private final ManufacturerService manufacturerService;
    private final ProductTypeService productTypeService;

    @Autowired
    public ProductController(ProductService productService, PhotoService photoService, ManufacturerService manufacturerService, ProductTypeService productTypeService) {
        this.productService = productService;
        this.photoService = photoService;
        this.manufacturerService = manufacturerService;
        this.productTypeService = productTypeService;
    }

    private void prepareModel(Model model) {
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("productTypes", productTypeService.findAll());
        model.addAttribute("photos", photoService.findAll());
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CREATE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("/new")
    String newProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("productPhotos", new ArrayList<>());
        model.addAttribute("isNew", true);
        prepareModel(model);
        return "product/product_form";
    }

    @PostMapping("")
    String createProduct(@Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("productPhotos", product.getPhotos());
            model.addAttribute("isNew", true);
            prepareModel(model);
            return "product/product_form";
        }
        productService.save(product);
        return "redirect:/products";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ READ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("")
    String getProductsList(Model model) {
        model.addAttribute("list", productService.findAll());
        return "product/products_list";
    }

    @GetMapping("/{id}")
    String getProductById(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        List<Photo> relatedPhotos = photoService.getByProductsContaining(product);
        model.addAttribute("product", product);
        model.addAttribute("photos", relatedPhotos);
        return "product/product_detail";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ UPDATE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("/{id}/edit")
    String editProduct(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("productPhotos", photoService.getByProductsContaining(product));
        prepareModel(model);
        return "product/product_form";
    }

    @PatchMapping("/{id}")
    String updateProduct(@PathVariable int id, @Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("productPhotos", product.getPhotos());
            prepareModel(model);
            return "product/product_form";
        }
        productService.update(id, product);
        return "redirect:/products";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DELETE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("/{id}/delete")
    String confirmDeletingProductById(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product/product_delete_confirmation";
    }

    @DeleteMapping("/{id}")
    String deleteProductById(@PathVariable int id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}
