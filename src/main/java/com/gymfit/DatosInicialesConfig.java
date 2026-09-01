package com.gymfit;

import com.gymfit.model.Producto;
import com.gymfit.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;

@Configuration
public class DatosInicialesConfig {
    @Bean
    CommandLineRunner productosIniciales(ProductoRepository productos) {
        return args -> {
            if (productos.count() != 0) return;
            crear(productos, "Proteína Whey Gold 1kg", "Recuperación muscular post entrenamiento.", "Suplementos", "25000", 20);
            crear(productos, "Creatina Micronizada 300g", "Creatina para fuerza y rendimiento.", "Suplementos", "30000", 15);
            crear(productos, "Pre-Workout Extreme 400g", "Energía y concentración para entrenar.", "Suplementos", "22000", 12);
        };
    }
    private void crear(ProductoRepository repo, String nombre, String descripcion, String categoria, String precio, int stock) {
        Producto p = new Producto(); p.setNombre(nombre); p.setDescripcion(descripcion);
        p.setCategoria(categoria); p.setPrecio(new BigDecimal(precio)); p.setStock(stock); repo.save(p);
    }
}
