package ru.zeovl.musicstore.controllers.admin;

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
@RequestMapping("/admin/products")
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
        model.addAttribute("manufacturers", manufacturerService.findAllOrderedById());
        model.addAttribute("productTypes", productTypeService.findAllOrderedById());
        model.addAttribute("photos", photoService.findAllOrderedById());
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
        return "admin/product/product_form";
    }

    @PostMapping("")
    String createProduct(@Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("productPhotos", new ArrayList<>());
            model.addAttribute("isNew", true);
            prepareModel(model);
            return "admin/product/product_form";
        }
        productService.save(product);
        return "redirect:/admin/products";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ READ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("")
    String getProductsList(Model model) {
        model.addAttribute("list", productService.findAllOrderedByID());
        return "admin/product/products_list";
    }

    @GetMapping("/{id}")
    String getProductById(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        List<Photo> relatedPhotos = photoService.getByProductsContaining(product);
        model.addAttribute("product", product);
        model.addAttribute("photos", relatedPhotos);
        return "admin/product/product_detail";
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
        return "admin/product/product_form";
    }

    @PatchMapping("/{id}")
    String updateProduct(@PathVariable int id, @Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("productPhotos", product.getPhotos());
            prepareModel(model);
            return "admin/product/product_form";
        }
        productService.update(id, product);
        return "redirect:/admin/products";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DELETE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("/{id}/delete")
    String confirmDeletingProductById(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "admin/product/product_delete_confirmation";
    }

    @DeleteMapping("/{id}")
    String deleteProductById(@PathVariable int id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }
}
