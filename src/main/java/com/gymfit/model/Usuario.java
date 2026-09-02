package com.gymfit.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


public class Usuario {

    private Long id;
    private Rol rol;
    private String email;
    private String password;

    private Persona persona;
    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
}

//fechaCreacion iria? , no creo que es innecesario