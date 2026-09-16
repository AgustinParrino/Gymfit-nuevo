package com.gymfit.controller;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EstadoController {
    @GetMapping("/estado")
    public Map<String, Object> estado() {
        return Map.of("aplicacion", "GymFit", "estado", "ACTIVA", "fecha", LocalDateTime.now());
    }
}
