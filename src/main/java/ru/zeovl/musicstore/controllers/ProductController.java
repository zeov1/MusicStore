package ru.zeovl.musicstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zeovl.musicstore.models.Manufacturer;
import ru.zeovl.musicstore.models.Photo;
import ru.zeovl.musicstore.models.Product;
import ru.zeovl.musicstore.models.ProductType;
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

    @GetMapping("/new")
    String newProduct(Model model) {
        Product product = new Product();
        List<Photo> productPhotos = new ArrayList<>();
        List<Manufacturer> manufacturers = manufacturerService.findAll();
        List<ProductType> productTypes = productTypeService.findAll();
        List<Photo> photos = photoService.findAll();
        model.addAttribute("product", product);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("productTypes", productTypes);
        model.addAttribute("photos", photos);
        model.addAttribute("productPhotos", productPhotos);
        return "product/product_form";
    }

    @PostMapping("")
    String createProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/products";
    }

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

    @GetMapping("/{id}/edit")
    String editProduct(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        List<Photo> productPhotos = photoService.getByProductsContaining(product);
        List<Manufacturer> manufacturers = manufacturerService.findAll();
        List<ProductType> productTypes = productTypeService.findAll();
        List<Photo> photos = photoService.findAll();
        model.addAttribute("product", product);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("productTypes", productTypes);
        model.addAttribute("photos", photos);
        model.addAttribute("productPhotos", productPhotos);
        System.out.println("\n\nProduct photos:");
        productPhotos.forEach(System.out::println);
        return "product/product_form";
    }

    @PatchMapping("/{id}")
    String updateProduct(@PathVariable int id, Product product) {
        productService.update(id, product);
        return "redirect:/products";
    }

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
