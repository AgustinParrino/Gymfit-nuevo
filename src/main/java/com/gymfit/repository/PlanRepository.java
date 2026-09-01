package com.gymfit.repository;

import com.gymfit.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByActivoTrueOrderByPrecioAsc();
}
