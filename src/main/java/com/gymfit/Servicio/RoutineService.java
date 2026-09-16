package com.gymfit.Servicio;

import com.gymfit.model.Routine;
import com.gymfit.repository.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    // Metodo para obtener todas las rutinas de la base de datos
    public List<Routine> getAllRoutines() {
        return routineRepository.findAll();
    }
}
