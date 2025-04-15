package ru.zeovl.musicstore.controllers.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zeovl.musicstore.models.Manufacturer;
import ru.zeovl.musicstore.models.Product;
import ru.zeovl.musicstore.services.ManufacturerService;
import ru.zeovl.musicstore.services.ProductService;

import java.util.List;

@Controller
@RequestMapping("/admin/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;
    private final ProductService productService;

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService, ProductService productService) {
        this.manufacturerService = manufacturerService;
        this.productService = productService;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CREATE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("/new")
    String newManufacturer(Model model) {
        Manufacturer manufacturer = new Manufacturer();
        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("isNew", true);
        return "admin/manufacturer/manufacturer_form";
    }

    @PostMapping("")
    String createManufacturer(@ModelAttribute @Valid Manufacturer manufacturer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isNew", true);
            return "admin/manufacturer/manufacturer_form";
        }
        manufacturerService.save(manufacturer);
        return "redirect:/admin/manufacturers";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ READ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("")
    String getManufacturersList(Model model) {
        model.addAttribute("list", manufacturerService.findAll());
        return "admin/manufacturer/manufacturers_list";
    }

    @GetMapping("/{id}")
    String getManufacturerById(@PathVariable int id, Model model) {
        Manufacturer manufacturer = manufacturerService.findById(id);
        List<Product> relatedProducts = productService.findByManufacturer(manufacturer);
        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("products", relatedProducts);
        return "admin/manufacturer/manufacturer_detail";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ UPDATE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("/{id}/edit")
    String editManufacturer(@PathVariable int id, Model model) {
        Manufacturer manufacturer = manufacturerService.findById(id);
        model.addAttribute("manufacturer", manufacturer);
        return "admin/manufacturer/manufacturer_form";
    }

    @PatchMapping("/{id}")
    String updateManufacturer(@PathVariable int id, @ModelAttribute @Valid Manufacturer manufacturer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/manufacturer/manufacturer_form";
        }
        manufacturerService.update(id, manufacturer);
        return "redirect:/admin/manufacturers";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DELETE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping("/{id}/delete")
    String confirmDeletingManufacturer(@PathVariable int id, Model model) {
        Manufacturer manufacturer = manufacturerService.findById(id);
        model.addAttribute("manufacturer", manufacturer);
        return "admin/manufacturer/manufacturer_delete_confirmation";
    }

    @DeleteMapping("/{id}")
    String deleteManufacturer(@PathVariable int id) {
        manufacturerService.deleteById(id);
        return "redirect:/admin/manufacturers";
    }
}
