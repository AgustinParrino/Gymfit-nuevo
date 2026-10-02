package com.gymfit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Component
public class SeedData implements CommandLineRunner {

    private final ExerciseRepository exercises;
    private final RoutineRepository routines;
    private final AdminRepository admins;
    private final PasswordEncoder encoder;
    private final DietRepository diets;

    @Value("${gymfit.admin.username}")
    String username;

    @Value("${gymfit.admin.password}")
    String password;

    public SeedData(ExerciseRepository e, RoutineRepository r, AdminRepository a, PasswordEncoder p, DietRepository d) {
        this.exercises = e;
        this.routines = r;
        this.admins = a;
        this.encoder = p;
        this.diets = d;
    }

    @Transactional
    public void run(String... args) {
        if (admins.count() == 0) {
            if (password.isBlank()) {
                password = UUID.randomUUID().toString();
                System.out.println("GYMFIT · Contraseña inicial de admin: " + password + " · Guardala; no volverá a mostrarse.");
            }
            if (password.length() < 10)
                throw new IllegalArgumentException("GYMFIT_ADMIN_PASSWORD debe tener al menos 10 caracteres");
            admins.save(new AdminAccount(username, encoder.encode(password)));
        }

        // Carga de Dietas (independiente de si ya hay ejercicios o no)
        if (diets.count() == 0) {
            diets.saveAll(List.of(
                    new Diet(
                            "Plan Hipertrofia Omnívora",
                            "Ganancia muscular",
                            "Omnívora",
                            "Plan alto en proteínas animales y carbohidratos complejos para soporte de entrenamiento pesado.",
                            "Desayuno: 3 huevos revueltos, 2 tostadas integrales y café con leche;Almuerzo: 200g pechuga de pollo con arroz blanco y ensalada verde;Merienda: Yogur griego con avena, frutos secos y una banana;Cena: 200g bife de lomo magro con batatas al horno y brócoli"
                    ),
                    new Diet(
                            "Plan Definición Vegana",
                            "Definición y rendimiento",
                            "Vegana",
                            "Nutrición 100% basada en plantas, equilibrando aminoácidos y controlando la ingesta calórica.",
                            "Desayuno: Bowl de avena, leche de almendras, semillas de chía, proteína vegetal y arándanos;Almuerzo: Ensalada completa de quinoa, tofu salteado, palta y garbanzos;Merienda: Batido de frutas con mantequilla de maní y tostón de centeno;Cena: Wok de seitán con verduras variadas y fideos de arroz"
                    ),
                    new Diet(
                            "Plan Balanceado Vegetariano",
                            "Mantenimiento y salud general",
                            "Vegetariana",
                            "Combinación equilibrada de legumbres, huevos y lácteos para una digestión liviana y energía sostenida.",
                            "Desayuno: Omelette de espinacas y queso magro con infusión y jugo de naranja;Almuerzo: Cazuela de lentejas con verduras y ensalada mixta;Merienda: Tazón de frutas de estación con frutos secos y semillas de zapallo;Cena: Hamburguesas de garbanzos con puré de calabaza y rodajas de tomate"
                    )
            ));
        }

        if (exercises.count() > 0) return;

        var data = exercises.saveAll(List.of(
                new Exercise("Sentadilla con barra", "Piernas", "Intermedio", "Barra y discos", "Apoyá la barra sobre la espalda alta. Descendé con el tronco estable y las rodillas alineadas con los pies. Usá una carga que permita mantener el control."),
                new Exercise("Press de banca", "Pecho", "Intermedio", "Banco y barra", "Con los pies apoyados, bajá la barra de forma controlada hacia el pecho y extendé los brazos sin perder la estabilidad."),
                new Exercise("Remo con mancuerna", "Espalda", "Principiante", "Mancuerna y banco", "Apoyá una mano en el banco. Llevá la mancuerna hacia la cadera manteniendo la espalda estable; evitá girar el tronco."),
                new Exercise("Press militar", "Hombros", "Intermedio", "Mancuernas", "Desde la altura de los hombros, empujá las mancuernas hacia arriba sin arquear la zona lumbar."),
                new Exercise("Curl de bíceps", "Brazos", "Principiante", "Mancuernas", "Flexioná los codos sin balancear el cuerpo. Descendé lentamente y mantené los codos cerca del tronco."),
                new Exercise("Plancha abdominal", "Core", "Principiante", "Colchoneta", "Apoyá antebrazos y puntas de los pies. Mantené el cuerpo alineado y la respiración fluida. Para ejercicios isométricos, las repeticiones indican segundos."),
                new Exercise("Peso muerto rumano", "Piernas", "Avanzado", "Barra y discos", "Llevá la cadera hacia atrás con una ligera flexión de rodillas. Mantené la barra cerca del cuerpo y la espalda estable."),
                new Exercise("Jalón al pecho", "Espalda", "Principiante", "Polea", "Llevá la barra hacia la parte superior del pecho, sin balancearte ni tirar detrás de la nuca.")
        ));

        String[] names = {"Fuerza · cuerpo completo", "Tren superior", "Piernas y core"};
        String[] days = {"Lunes", "Miércoles", "Viernes"};
        int[][] ids = {{0, 1, 2, 5}, {1, 2, 3, 4}, {0, 6, 5}};

        for (int i = 0; i < 3; i++) {
            Routine r = new Routine();
            r.name = names[i];
            r.goal = "Fuerza general";
            r.day = days[i];
            r.difficulty = "Intermedio";
            for (int j : ids[i]) {
                RoutineItem t = new RoutineItem();
                t.routine = r;
                t.exercise = data.get(j);
                t.position = r.items.size();
                t.sets = 3;
                t.reps = j == 5 ? 30 : 10;
                t.restSeconds = 60;
                r.items.add(t);
            }
            routines.save(r);
        }
    }
}