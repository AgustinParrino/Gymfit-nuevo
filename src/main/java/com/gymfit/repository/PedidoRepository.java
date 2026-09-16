package com.gymfit.repository;

import com.gymfit.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAllByOrderByFechaDesc();
    List<Pedido> findByClienteIdOrderByFechaDesc(Long clienteId);
}
