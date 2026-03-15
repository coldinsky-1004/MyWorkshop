package com.myworkshop.ordersystem.controller;

import com.myworkshop.ordersystem.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final ProductRepository productRepository;

    public PageController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }
}
