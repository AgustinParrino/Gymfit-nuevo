package com.gymfit.controller;

import com.gymfit.model.Producto;
import com.gymfit.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoRepository productos;

    public ProductoController(ProductoRepository productos) { this.productos = productos; }

    @GetMapping
    public List<Producto> listar() { return productos.findByActivoTrueOrderByNombreAsc(); }

    @GetMapping("/{id}")
    public Producto buscar(@PathVariable Long id) { return obtener(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@Valid @RequestBody Producto producto) {
        validar(producto);
        producto.setId(null);
        producto.setActivo(true);
        return productos.save(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto entrada) {
        validar(entrada);
        Producto producto = obtener(id);
        producto.setNombre(entrada.getNombre().trim());
        producto.setDescripcion(entrada.getDescripcion());
        producto.setPrecio(entrada.getPrecio());
        producto.setStock(entrada.getStock());
        producto.setCategoria(entrada.getCategoria());
        return productos.save(producto);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desactivar(@PathVariable Long id) {
        Producto producto = obtener(id);
        producto.setActivo(false);
        productos.save(producto);
    }

    private Producto obtener(Long id) {
        return productos.findById(id).filter(Producto::isActivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto inexistente"));
    }

    private void validar(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio debe ser mayor a cero");
        if (producto.getStock() == null || producto.getStock() < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El stock no puede ser negativo");
    }
}
