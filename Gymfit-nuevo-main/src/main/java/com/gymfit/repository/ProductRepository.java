package com.gymfit.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gymfit.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> { }
