package com.gymfit.repository;

import com.gymfit.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findFirstByWhatsapp(String whatsapp);
    Optional<Cliente> findByDni(String dni);
}
