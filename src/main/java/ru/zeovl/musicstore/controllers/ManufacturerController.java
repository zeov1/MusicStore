package ru.zeovl.musicstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zeovl.musicstore.models.Manufacturer;
import ru.zeovl.musicstore.models.Product;
import ru.zeovl.musicstore.services.ManufacturerService;
import ru.zeovl.musicstore.services.ProductService;

import java.util.List;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;
    private final ProductService productService;

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService, ProductService productService) {
        this.manufacturerService = manufacturerService;
        this.productService = productService;
    }

    @GetMapping("")
    String getManufacturersList(Model model) {
        model.addAttribute("list", manufacturerService.findAll());
        return "manufacturers_list";
    }

    @GetMapping("/{id}")
    String getManufacturerById(@PathVariable int id, Model model) {
        Manufacturer manufacturer = manufacturerService.findById(id);
        List<Product> relatedProducts = productService.findByManufacturer(manufacturer);
        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("products", relatedProducts);
        return "manufacturer_detail";
    }
}
