package com.gymfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DietController {

    @GetMapping("/diets")
    public String listaDietas() {
        return "diets"; // → va a diets.html
    }

    @GetMapping("/diet/omnivoro")
    public String omnivoro(Model model) {
        model.addAttribute("titulo", "Dieta Omnívora");
        model.addAttribute("descripcion", "Ideal para ganancia muscular");
        model.addAttribute("meals", new String[]{
                "Desayuno: 4 huevos + 100g avena + banana",
                "Almuerzo: 200g pollo + 150g arroz + brócoli",
                "Merienda: Yogur griego + almendras",
                "Cena: 180g salmón + batata + ensalada"
        });
        return "diet-detail";
    }

    @GetMapping("/diet/vegano")
    public String vegano(Model model) {
        model.addAttribute("titulo", "Dieta Vegana");
        model.addAttribute("descripcion", "100% vegetal - Perfecta para definición");
        model.addAttribute("meals", new String[]{
                "Desayuno: Batido proteína vegana + avena + banana",
                "Almuerzo: Lentejas + arroz integral + vegetales",
                "Merienda: Hummus con zanahoria",
                "Cena: Tofu salteado + quinoa + brócoli"
        });
        return "diet-detail";
    }

    @GetMapping("/diet/vegetariano")
    public String vegetariano(Model model) {
        model.addAttribute("titulo", "Dieta Vegetariana");
        model.addAttribute("descripcion", "Sin carne, con lácteos y huevos");
        model.addAttribute("meals", new String[]{
                "Desayuno: Yogur griego + granola + frutas",
                "Almuerzo: Tortilla de espinacas + queso + ensalada",
                "Merienda: Queso cottage + tomate",
                "Cena: Pasta integral con pesto + parmesano"
        });
        return "diet-detail";
    }
}