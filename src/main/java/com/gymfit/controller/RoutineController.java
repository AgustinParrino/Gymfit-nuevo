package com.gymfit.controller;

import com.gymfit.model.Routine;
import com.gymfit.Servicio.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/routines")
public class RoutineController {

    @Autowired
    private RoutineService routineService; // Inyectamos el servicio

    @GetMapping
    public String showRoutines(Model model) {
        // Obtenemos los datos reales desde la base de datos
        List<Routine> allRoutines = routineService.getAllRoutines();

        // Agrupamos por día manteniendo el orden: Día 1, Día 2, Día 3...
        Map<String, List<Routine>> routinesByDay = new LinkedHashMap<>();
        for (Routine r : allRoutines) {
            routinesByDay.computeIfAbsent(r.getDay(), k -> new ArrayList<>()).add(r);
        }

        model.addAttribute("routinesByDay", routinesByDay);
        return "routines"; // Renderiza el archivo routines.html
    }
}