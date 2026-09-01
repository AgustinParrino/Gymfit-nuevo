package com.gymfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

import com.gymfit.repository.ProductRepository;
import com.gymfit.repository.MealRepository;
import com.gymfit.repository.RoutineRepository;
import com.gymfit.repository.UserRepository;

@Controller
public class HomeController {
    private final ProductRepository productRepo;
    private final MealRepository mealRepo;
    private final RoutineRepository routineRepo;
    private final UserRepository userRepo;

    public HomeController(ProductRepository p, MealRepository m, RoutineRepository r, UserRepository u) {
        this.productRepo = p; this.mealRepo = m; this.routineRepo = r; this.userRepo = u;
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        Object uid = session.getAttribute("userId");
        if (uid==null) return "redirect:/auth/login";
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("meals", mealRepo.findAll());
        model.addAttribute("routines", routineRepo.findAll());
        return "index";
    }
}
