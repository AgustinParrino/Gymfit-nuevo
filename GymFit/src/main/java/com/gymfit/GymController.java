package com.gymfit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;

@Controller
public class GymController {

    final ExerciseRepository exercises;
    final RoutineRepository routines;
    final LogRepository logs;
    final DietRepository diets;

    static final List<String> MUSCLES = List.of("Piernas", "Pecho", "Espalda", "Hombros", "Brazos", "Core", "Cardio");
    static final List<String> LEVELS = List.of("Principiante", "Intermedio", "Avanzado");
    static final List<String> DAYS = List.of("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo");

    public GymController(ExerciseRepository e, RoutineRepository r, LogRepository l, DietRepository d) {
        exercises = e;
        routines = r;
        logs = l;
        diets = d;
    }

    @ModelAttribute
    public void shared(Model m) {
        m.addAttribute("muscles", MUSCLES);
        m.addAttribute("levels", LEVELS);
        m.addAttribute("days", DAYS);
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/")
    String dashboard(Model m) {
        var history = logs.findAll().stream()
                .sorted(Comparator.comparing((WorkoutLog l) -> l.date).thenComparing(l -> l.id).reversed())
                .toList();
        m.addAttribute("page", "dashboard");
        m.addAttribute("routineCount", routines.count());
        m.addAttribute("exerciseCount", exercises.count());
        m.addAttribute("dietCount", diets.count());
        m.addAttribute("logCount", logs.count());
        m.addAttribute("totalMinutes", history.stream().mapToInt(l -> l.minutes).sum());
        m.addAttribute("routines", routines.findAll());
        m.addAttribute("logs", history.stream().limit(5).toList());
        m.addAttribute("today", LocalDate.now());
        m.addAttribute("week", java.util.stream.IntStream.range(0, 7).mapToObj(i -> {
            LocalDate d = LocalDate.now().minusDays(6 - i);
            return Map.of("label", d.getDayOfMonth() + "/" + d.getMonthValue(), "value",
                    history.stream().filter(l -> l.date.equals(d)).mapToInt(l -> l.minutes).sum());
        }).toList());
        return "dashboard";
    }

    // --- MÓDULO DIETAS ---
    @GetMapping("/diets")
    String dietList(Model m) {
        m.addAttribute("page", "diets");
        m.addAttribute("diets", diets.findAll());
        return "diets";
    }

    @GetMapping("/diets/{id}")
    String dietDetail(@PathVariable Long id, Model m) {
        Diet diet = diets.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        m.addAttribute("page", "diets");
        m.addAttribute("diet", diet);
        m.addAttribute("mealsList", Arrays.asList(diet.meals.split(";")));
        return "diet-detail";
    }

    // --- EJERCICIOS ---
    @GetMapping("/exercises")
    String exerciseList(@RequestParam(defaultValue = "") String q, @RequestParam(defaultValue = "") String muscle, Model m) {
        m.addAttribute("page", "exercises");
        m.addAttribute("q", q);
        m.addAttribute("muscle", muscle);
        m.addAttribute("exercises", exercises.findAll().stream()
                .filter(e -> e.name.toLowerCase(Locale.ROOT).contains(q.toLowerCase(Locale.ROOT))
                        && (muscle.isBlank() || e.muscle.equals(muscle)))
                .toList());
        return "exercises";
    }

    @GetMapping("/exercises/new")
    String newExercise(Model m) {
        m.addAttribute("exercise", new Exercise());
        m.addAttribute("page", "exercises");
        return "exercise-form";
    }

    @GetMapping("/exercises/{id}/edit")
    String editExercise(@PathVariable Long id, Model m) {
        m.addAttribute("exercise", exercise(id));
        m.addAttribute("page", "exercises");
        return "exercise-form";
    }

    @PostMapping("/exercises/save")
    @Transactional
    String saveExercise(@RequestParam(required = false) Long id, @RequestParam String name,
                        @RequestParam String muscle, @RequestParam String difficulty,
                        @RequestParam String equipment, @RequestParam String description, RedirectAttributes a) {
        Exercise e = id == null ? new Exercise() : exercise(id);
        e.name = text(name, 100);
        e.muscle = choice(muscle, MUSCLES);
        e.difficulty = choice(difficulty, LEVELS);
        e.equipment = text(equipment, 100);
        e.description = text(description, 2000);
        exercises.save(e);
        a.addFlashAttribute("success", "Ejercicio guardado.");
        return "redirect:/exercises";
    }

    @PostMapping("/exercises/{id}/delete")
    @Transactional
    String deleteExercise(@PathVariable Long id, RedirectAttributes a) {
        Exercise e = exercise(id);
        if (routines.findAll().stream().anyMatch(r -> r.items.stream().anyMatch(t -> t.exercise.id.equals(id)))) {
            a.addFlashAttribute("error", "Este ejercicio se usa en una rutina. Quitalo de la rutina antes de eliminarlo.");
        } else {
            exercises.delete(e);
            a.addFlashAttribute("success", "Ejercicio eliminado.");
        }
        return "redirect:/exercises";
    }

    // --- RUTINAS ---
    @GetMapping("/routines")
    String routineList(Model m) {
        m.addAttribute("page", "routines");
        m.addAttribute("routines", routines.findAll());
        return "routines";
    }

    @GetMapping("/routines/new")
    String newRoutine(Model m) {
        m.addAttribute("routine", new Routine());
        m.addAttribute("exercises", exercises.findAll());
        m.addAttribute("page", "routines");
        return "routine-form";
    }

    @GetMapping("/routines/{id}")
    String detail(@PathVariable Long id, Model m) {
        m.addAttribute("routine", routine(id));
        m.addAttribute("today", LocalDate.now());
        m.addAttribute("page", "routines");
        return "routine-detail";
    }

    @GetMapping("/routines/{id}/edit")
    String editRoutine(@PathVariable Long id, Model m) {
        m.addAttribute("routine", routine(id));
        m.addAttribute("exercises", exercises.findAll());
        m.addAttribute("page", "routines");
        return "routine-form";
    }

    @PostMapping("/routines/save")
    @Transactional
    String saveRoutine(@RequestParam(required = false) Long id, @RequestParam String name,
                       @RequestParam String goal, @RequestParam String day, @RequestParam String difficulty,
                       @RequestParam List<Long> exerciseId, @RequestParam List<Integer> sets,
                       @RequestParam List<Integer> reps, @RequestParam List<Integer> rest, RedirectAttributes a) {
        if (exerciseId.isEmpty() || exerciseId.size() > 30 || sets.size() != exerciseId.size()
                || reps.size() != exerciseId.size() || rest.size() != exerciseId.size())
            throw bad();
        Routine r = id == null ? new Routine() : routine(id);
        r.name = text(name, 100);
        r.goal = text(goal, 100);
        r.day = choice(day, DAYS);
        r.difficulty = choice(difficulty, LEVELS);
        List<RoutineItem> items = new ArrayList<>();
        for (int i = 0; i < exerciseId.size(); i++) {
            RoutineItem t = new RoutineItem();
            t.routine = r;
            t.exercise = exercise(exerciseId.get(i));
            t.position = i;
            t.sets = number(sets.get(i), 1, 20);
            t.reps = number(reps.get(i), 1, 300);
            t.restSeconds = number(rest.get(i), 0, 600);
            items.add(t);
        }
        r.items.clear();
        r.items.addAll(items);
        routines.save(r);
        a.addFlashAttribute("success", "Rutina guardada.");
        return "redirect:/routines";
    }

    @PostMapping("/routines/{id}/delete")
    @Transactional
    String deleteRoutine(@PathVariable Long id, RedirectAttributes a) {
        routines.delete(routine(id));
        a.addFlashAttribute("success", "Rutina eliminada. El historial se conserva.");
        return "redirect:/routines";
    }

    @PostMapping("/routines/{id}/log")
    String log(@PathVariable Long id, @RequestParam LocalDate date, @RequestParam int minutes,
               @RequestParam(defaultValue = "") String notes, RedirectAttributes a) {
        if (date == null || date.isAfter(LocalDate.now())) throw bad();
        WorkoutLog l = new WorkoutLog();
        l.routineName = routine(id).name;
        l.date = date;
        l.minutes = number(minutes, 1, 600);
        if (notes.length() > 1000) throw bad();
        l.notes = notes.strip();
        logs.save(l);
        a.addFlashAttribute("success", "Entrenamiento registrado.");
        return "redirect:/history";
    }

    @GetMapping("/history")
    String history(Model m) {
        m.addAttribute("page", "history");
        m.addAttribute("logs", logs.findAll().stream()
                .sorted(Comparator.comparing((WorkoutLog l) -> l.date).thenComparing(l -> l.id).reversed())
                .toList());
        return "history";
    }

    @PostMapping("/history/{id}/delete")
    String deleteLog(@PathVariable Long id, RedirectAttributes a) {
        logs.delete(logs.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        a.addFlashAttribute("success", "Registro eliminado.");
        return "redirect:/history";
    }

    Exercise exercise(Long id) {
        return exercises.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    Routine routine(Long id) {
        return routines.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    static ResponseStatusException bad() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Revisá los datos ingresados");
    }

    static String text(String s, int max) {
        if (s == null || s.isBlank() || s.strip().length() > max) throw bad();
        return s.strip();
    }

    static String choice(String s, List<String> options) {
        if (!options.contains(s)) throw bad();
        return s;
    }

    static int number(int n, int min, int max) {
        if (n < min || n > max) throw bad();
        return n;
    }
}