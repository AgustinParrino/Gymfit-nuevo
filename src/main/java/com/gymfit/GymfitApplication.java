package com.gymfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.gymfit.model.*;
import com.gymfit.repository.*;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class GymfitApplication {
    public static void main(String[] args) {
        SpringApplication.run(GymfitApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(UserRepository userRepo, ProductRepository productRepo, DietRepository dietRepo, RoutineRepository routineRepo, MealRepository mealRepo) {
        return args -> {
            if (userRepo.count()==0) {
                userRepo.save(new User(null, "Agus", "agus@example.com", "1234"));
            }
            if (productRepo.count()==0) {
                productRepo.save(new Product(null, "Agua energizante", "Agua con electrolitos para rehidratación rápida.", "bebida", new BigDecimal("199.99"), "500ml", "img/agua.jpg"));
                productRepo.save(new Product(null, "Vitamina C", "Cápsulas de vitamina C para recuperación.", "suplemento", new BigDecimal("599.99"), "60 caps", "img/vitamina.jpg"));
                productRepo.save(new Product(null, "Barra Proteica", "Barra energética para después del ejercicio.", "suplemento", new BigDecimal("349.99"), "60g", "img/barra.jpg"));
            }
            if (productRepo.count() == 3) {
                productRepo.save(new Product(null, "Creatina Monohidrato", "Suplemento para aumentar la fuerza y masa muscular.", "suplemento", new BigDecimal("899.99"), "300g", "img/creatina.jpg"));
                productRepo.save(new Product(null, "Whey Protein", "Proteína de suero para recuperación post-entreno.", "suplemento", new BigDecimal("1299.99"), "1kg", "img/whey.jpg"));
                productRepo.save(new Product(null, "BCAAs 2:1:1", "Aminoácidos para reducir fatiga muscular.", "suplemento", new BigDecimal("799.99"), "200g", "img/bcaa.jpg"));
            }
            if (dietRepo.count()==0) {
                dietRepo.save(new Diet(null, "Dieta Vegetariana", "Baja en carne, rica en vegetales.", "Desayuno: Avena con frutas\\nAlmuerzo: Ensalada con legumbres\\nCena: Tofu salteado"));
                dietRepo.save(new Diet(null, "Dieta Omnívora", "Combinación equilibrada de proteínas y carbohidratos.", "Desayuno: Huevos y tostadas\\nAlmuerzo: Pollo y arroz\\nCena: Pescado y vegetales"));
                dietRepo.save(new Diet(null, "Dieta Vegana", "Sin productos animales, basada en plantas.", "Desayuno: Smoothie verde\\nAlmuerzo: Quinoa con vegetales\\nCena: Curry de garbanzos"));
            }
            if (routineRepo.count()==0) {
                routineRepo.save(new Routine(null, "Piernas", "Sentadillas 4x10; Zancadas 3x12; Peso muerto rumano 3x8", "Intermedio", null));
                routineRepo.save(new Routine(null, "Brazos", "Curl de bíceps 3x12; Fondos 3x10; Extensiones triceps 3x12", "Intermedio", null));
                routineRepo.save(new Routine(null, "Pecho", "Press banca 4x8; Aperturas 3x12; Flexiones 3x15", "Intermedio", null));
            }
            if (mealRepo.count()==0) {
                Diet veg = dietRepo.findAll().get(0);
                Diet omni = dietRepo.findAll().get(1);
                Diet vegn = dietRepo.findAll().get(2);
                mealRepo.save(new Meal(null, "Vegetariana", veg));
                mealRepo.save(new Meal(null, "Omnivora", omni));
                mealRepo.save(new Meal(null, "Vegana", vegn));
            }
        };
    }
}
