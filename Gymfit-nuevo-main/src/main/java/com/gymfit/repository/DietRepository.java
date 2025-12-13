package com.gymfit.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gymfit.model.Diet;

public interface DietRepository extends JpaRepository<Diet, Long> { }
