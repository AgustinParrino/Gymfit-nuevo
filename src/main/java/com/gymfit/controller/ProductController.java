package com.gymfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.servlet.http.HttpSession;

import com.gymfit.repository.ProductRepository;
import com.gymfit.model.Product;

@Controller
public class ProductController {
    private final ProductRepository productRepo;
    public ProductController(ProductRepository productRepo) { this.productRepo = productRepo; }

    @GetMapping("/products")
    public String products(Model model, HttpSession session) {
        Object uid = session.getAttribute("userId"); if (uid==null) return "redirect:/auth/login";
        model.addAttribute("products", productRepo.findAll());
        return "products";
    }

    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable Long id, Model model, HttpSession session) {
        Object uid = session.getAttribute("userId"); if (uid==null) return "redirect:/auth/login";
        Product p = productRepo.findById(id).orElse(null);
        model.addAttribute("product", p);
        return "product-detail";
    }
}
