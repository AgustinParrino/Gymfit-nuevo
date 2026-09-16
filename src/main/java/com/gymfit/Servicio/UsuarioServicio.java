package com.gymfit.Servicio;

import com.gymfit.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioServicio {
public final UserRepository usuarios;


    public UsuarioServicio(UserRepository usuarios) {
        this.usuarios = usuarios;
    }


//    public
}
