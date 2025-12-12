package com.gymfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.servlet.http.HttpSession;

import com.gymfit.repository.DietRepository;
import com.gymfit.repository.RoutineRepository;
import com.gymfit.model.Diet;
import com.gymfit.model.Routine;

import java.util.List;

@Controller
public class MenuController {

    private final DietRepository dietRepo;
    private final RoutineRepository routineRepo;

    public MenuController(DietRepository dietRepo, RoutineRepository routineRepo) {
        this.dietRepo = dietRepo; this.routineRepo = routineRepo;
    }

    @GetMapping("/menu/{type}")
    public String menu(@PathVariable String type, Model model, HttpSession session) {
        Object uid = session.getAttribute("userId"); if (uid==null) return "redirect:/auth/login";
        Diet diet = dietRepo.findAll().stream().filter(d -> d.getTitle().toLowerCase().contains(type.toLowerCase())).findFirst().orElse(null);
        List<Routine> routines = routineRepo.findAll();
        model.addAttribute("type", type);
        model.addAttribute("diet", diet);
        model.addAttribute("routines", routines);
        return "menu";
    }
}
