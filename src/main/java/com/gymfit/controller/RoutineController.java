package com.gymfit.controller;


import com.gymfit.model.Routine;
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

    @GetMapping
    public String showRoutines(Model model) {
        List<Routine> allRoutines = getSampleRoutines(); // ← Aquí iría tu repository en el futuro

        // Agrupamos por día manteniendo el orden: Día 1, Día 2, Día 3...
        Map<String, List<Routine>> routinesByDay = new LinkedHashMap<>();
        for (Routine r : allRoutines) {
            routinesByDay.computeIfAbsent(r.getDay(), k -> new ArrayList<>()).add(r);
        }

        model.addAttribute("routinesByDay", routinesByDay);
        return "routines"; // → va al archivo routines.html que ya tienes
    }

    // Datos de ejemplo (luego lo cambias por tu repositorio)
    private List<Routine> getSampleRoutines() {
        List<Routine> list = new ArrayList<>();

        list.add(Routine.builder().title("Pecho + Tríceps").notes("Bench press 4×8-10\nInclined dumbbell 3×10\nFunds 4×12\nFrench press 4×10").day("Día 1").build());
        list.add(Routine.builder().title("Espalda + Bíceps").notes("Dominadas 4×máx\nRemo con barra 4×8\nFace pull 3×15\nCurl bíceps 4×12").day("Día 2").build());
        list.add(Routine.builder().title("Piernas").notes("Sentadillas 5×6-8\nPrensa 4×12\nZancadas 3×10 c/p\nGemelos 4×15").day("Día 3").build());
        list.add(Routine.builder().title("Hombros + Core").notes("Press militar 4×8\nElevaciones laterales 4×12\nPájaro 3×15\nPlancha + elevaciones piernas").day("Día 4").build());
        list.add(Routine.builder().title("Full Body / Repetición").notes("Día de técnica o ejercicios débiles").day("Día 5").build());
        list.add(Routine.builder().title("Descanso activo").notes("Caminar, movilidad, yoga o descanso total").day("Día 6").build());
        list.add(Routine.builder().title("Descanso").notes("Recuperación total").day("Día 7").build());

        return list;
    }
}
