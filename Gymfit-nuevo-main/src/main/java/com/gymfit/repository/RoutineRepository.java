package com.gymfit.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gymfit.model.Routine;

public interface RoutineRepository extends JpaRepository<Routine, Long> { }
