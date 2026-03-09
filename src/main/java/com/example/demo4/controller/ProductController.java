package com.example.demo4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo4.model.Product;
import com.example.demo4.service.ProductService;

@Controller
public class ProductController {

    @Autowired
    private ProductService service;

    // LIST PRODUCT
    @GetMapping("/products")
    public String listProducts(Model model){
        model.addAttribute("products", service.getAllProducts());
        return "products";
    }

    // FORM ADD
    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("product", new Product());
        return "add_product";
    }

    // SAVE PRODUCT
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product){
        service.save(product);
        return "redirect:/products";
    }

    // DELETE PRODUCT
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        service.delete(id);
        return "redirect:/products";
    }

    // FORM EDIT
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        Product product = service.getProductById(id);
        model.addAttribute("product", product);
        return "edit_product";
    }

    // UPDATE PRODUCT
    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product){
        service.save(product);
        return "redirect:/products";
    }
}