package com.gymfit.Servicio;

import com.gymfit.model.Routine;
import com.gymfit.repository.RoutineRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutineDataLoader {

    @Bean
    CommandLineRunner initRoutines(RoutineRepository repository) {
        return args -> {
            // Solo cargamos datos si la tabla está vacía para no duplicarlos
            if (repository.count() == 0) {

                repository.save(Routine.builder()
                        .title("Pecho + Tríceps")
                        .notes("Bench press 4×8-10\nInclined dumbbell 3×10\nFrench press 4×10")
                        .day("Día 1")
                        .level("Intermedio")
                        .build());

                repository.save(Routine.builder()
                        .title("Espalda + Bíceps")
                        .notes("Dominadas 4×máx\nRemo con barra 4×8\nCurl bíceps 4×12")
                        .day("Día 2")
                        .level("Avanzado")
                        .build());

                repository.save(Routine.builder()
                        .title("Piernas")
                        .notes("Sentadillas 5×6-8\nPrensa 4×12\nGemelos 4×15")
                        .day("Día 3")
                        .level("Principiante")
                        .build());

                System.out.println("¡Rutinas de prueba cargadas con éxito!");
            }
        };
    }
}