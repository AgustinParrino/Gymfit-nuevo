package com.gymfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DietController {

    // Página principal con las 3 tarjetas
    @GetMapping("/diets")
    public String listaDietas() {
        return "diets";  // → busca diets.html (minúsculas)
    }

    // Detalle Omnívora
    @GetMapping("/diet/omnivoro")
    public String omnivoro(Model model) {
        model.addAttribute("titulo", "Dieta Omnívora");
        model.addAttribute("descripcion", "La más efectiva para ganar músculo y fuerza. Incluye proteínas animales de alta calidad.");
        model.addAttribute("meals", new String[]{
                "Desayuno: 4 huevos enteros + 100g avena + 1 banana",
                "Almuerzo: 200g pollo a la plancha + 150g arroz integral + brócoli al vapor",
                "Merienda: Yogur griego natural + 30g almendras + miel",
                "Cena: 180g salmón + batata asada + ensalada mixta"
        });
        return "diet-detail";  // → busca diet-detail.html
    }

    // Detalle Vegana
    @GetMapping("/diet/vegano")
    public String vegano(Model model) {
        model.addAttribute("titulo", "Dieta Vegana");
        model.addAttribute("descripcion", "100% vegetal. Ideal para definición y salud cardiovascular.");
        model.addAttribute("meals", new String[]{
                "Desayuno: Batido de proteína vegana + 80g avena + banana + espinaca",
                "Almuerzo: 200g lentejas cocidas + 150g quinoa + vegetales asados",
                "Merienda: Hummus casero + zanahorias + apio",
                "Cena: Tofu salteado con salsa de soja + 150g arroz integral + brócoli"
        });
        return "diet-detail";
    }

    // Detalle Vegetariana
    @GetMapping("/diet/vegetariano")
    public String vegetariano(Model model) {
        model.addAttribute("titulo", "Dieta Vegetariana");
        model.addAttribute("descripcion", "Sin carne pero con huevos y lácteos. Excelente balance proteico.");
        model.addAttribute("meals", new String[]{
                "Desayuno: Yogur griego + granola + frutas rojas + miel",
                "Almuerzo: Tortilla de 4 huevos con espinacas + queso fresco + ensalada",
                "Merienda: Queso cottage + tomate cherry + nueces",
                "Cena: Pasta integral con pesto + parmesano rallado + champiñones"
        });
        return "diet-detail";
    }
}