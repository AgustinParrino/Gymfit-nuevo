package com.gymfit.repository;

import com.gymfit.model.Diet;
import com.gymfit.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByDiet(Diet diet);
}
