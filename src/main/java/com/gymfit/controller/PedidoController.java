package com.gymfit.controller;

import com.gymfit.model.*;
import com.gymfit.repository.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoRepository pedidos;
    private final ProductoRepository productos;
    private final ClienteRepository clientes;

    public PedidoController(PedidoRepository pedidos, ProductoRepository productos, ClienteRepository clientes) {
        this.pedidos = pedidos;
        this.productos = productos;
        this.clientes = clientes;
    }

    @GetMapping
    public List<Pedido> listar() { return pedidos.findAllByOrderByFechaDesc(); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Transactional
    public Pedido crear(@Valid @RequestBody PedidoCrearRequest datos) {
        Producto producto = productos.buscarActivoParaActualizar(datos.productoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto inexistente"));
        if (producto.getStock() < datos.cantidad())
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Stock insuficiente. Disponible: " + producto.getStock());

        String telefono = datos.whatsapp().replaceAll("\\s+", "").trim();
        Cliente cliente = clientes.findFirstByWhatsapp(telefono).orElseGet(Cliente::new);
        cliente.setNombre(datos.clienteNombre().trim());
        cliente.setWhatsapp(telefono);
        cliente.setObjetivo(textoOpcional(datos.objetivo()));
        cliente = clientes.save(cliente);

        producto.setStock(producto.getStock() - datos.cantidad());
        productos.save(producto);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProducto(producto);
        pedido.setCantidad(datos.cantidad());
        pedido.setPrecioUnitario(producto.getPrecio());
        pedido.setTotal(producto.getPrecio().multiply(BigDecimal.valueOf(datos.cantidad())));
        pedido.setEstado(EstadoPedido.PENDIENTE);
        return pedidos.save(pedido);
    }

    @PatchMapping("/{id}/estado")
    public Pedido cambiarEstado(@PathVariable Long id, @RequestParam EstadoPedido estado) {
        Pedido pedido = pedidos.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido inexistente"));
        if (estado == EstadoPedido.CANCELADO)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usá la acción cancelar para devolver el stock");
        boolean valida = pedido.getEstado() == EstadoPedido.PENDIENTE && estado == EstadoPedido.CONFIRMADO
                || pedido.getEstado() == EstadoPedido.CONFIRMADO && estado == EstadoPedido.ENTREGADO;
        if (!valida)
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede pasar de " + pedido.getEstado() + " a " + estado);
        pedido.setEstado(estado);
        return pedidos.save(pedido);
    }

    @PostMapping("/{id}/cancelar") @Transactional
    public Pedido cancelar(@PathVariable Long id) {
        Pedido pedido = pedidos.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido inexistente"));
        if (pedido.getEstado() == EstadoPedido.CANCELADO) return pedido;
        if (pedido.getEstado() == EstadoPedido.ENTREGADO)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede cancelar un pedido entregado");
        Producto producto = pedido.getProducto();
        producto.setStock(producto.getStock() + pedido.getCantidad());
        productos.save(producto);
        pedido.setEstado(EstadoPedido.CANCELADO);
        return pedidos.save(pedido);
    }

    private String textoOpcional(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }
}
