package com.gymfit.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "clientes", indexes = @Index(name = "idx_cliente_whatsapp", columnList = "whatsapp"))
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String nombre;
    @Column(nullable = false, length = 30)
    private String whatsapp;
    @Column(length = 160)
    private String objetivo;
    @Column(unique = true, length = 20)
    private String dni;
    private String email;
    @Column(nullable = false)
    private LocalDate fechaAlta = LocalDate.now();
    @Column(nullable = false)
    private boolean activo = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getWhatsapp() { return whatsapp; }
    public void setWhatsapp(String whatsapp) { this.whatsapp = whatsapp; }
    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
