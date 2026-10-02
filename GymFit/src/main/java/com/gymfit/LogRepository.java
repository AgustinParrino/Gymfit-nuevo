package com.gymfit;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LogRepository extends JpaRepository<WorkoutLog,Long> {}
