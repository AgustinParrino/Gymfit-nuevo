package com.gymfit.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

public class Persona {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String telefono;


    @OneToMany(mappedBy = "persona ", cascade = CascadeType.ALL)
    private List<Usuario> usuarios= new arrayList<>();

}
