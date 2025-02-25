package ru.zeovl.musicstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("")
    String persistManufacturer(Manufacturer manufacturer) {
        manufacturerService.save(manufacturer);
        return "redirect:/manufacturers";
    }

    @PatchMapping("/{id}")
    String updateManufacturer(@PathVariable int id, Manufacturer manufacturer) {
        manufacturerService.update(id, manufacturer);
        return "redirect:/manufacturers";
    }

    @DeleteMapping("/{id}")
    String deleteManufacturer(@PathVariable int id) {
        manufacturerService.deleteById(id);
        return "redirect:/manufacturers";
    }

    @GetMapping("")
    String getManufacturersList(Model model) {
        model.addAttribute("list", manufacturerService.findAll());
        return "manufacturer/manufacturers_list";
    }

    @GetMapping("/{id}")
    String getManufacturerById(@PathVariable int id, Model model) {
        Manufacturer manufacturer = manufacturerService.findById(id);
        List<Product> relatedProducts = productService.findByManufacturer(manufacturer);
        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("products", relatedProducts);
        return "manufacturer/manufacturer_detail";
    }

    @GetMapping("/new")
    String newManufacturer(Model model) {
        Manufacturer manufacturer = new Manufacturer();
        model.addAttribute("manufacturer", manufacturer);
        return "manufacturer/manufacturer_form";
    }

    @GetMapping("/{id}/edit")
    String editManufacturer(@PathVariable int id, Model model) {
        Manufacturer manufacturer = manufacturerService.findById(id);
        model.addAttribute("manufacturer", manufacturer);
        return "manufacturer/manufacturer_form";
    }

    @GetMapping("/{id}/delete")
    String confirmDeletingManufacturer(@PathVariable int id, Model model) {
        Manufacturer manufacturer = manufacturerService.findById(id);
        model.addAttribute("manufacturer", manufacturer);
        return "manufacturer/manufacturer_delete_confirmation";
    }
}
