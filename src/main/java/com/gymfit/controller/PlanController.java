package com.gymfit.controller;

import com.gymfit.model.Plan;
import com.gymfit.repository.PlanRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {
    private final PlanRepository planes;
    public PlanController(PlanRepository planes) { this.planes = planes; }

    @GetMapping public List<Plan> listar() { return planes.findByActivoTrueOrderByPrecioAsc(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Plan crear(@RequestBody Plan plan) { plan.setId(null); plan.setActivo(true); return planes.save(plan); }
    @PutMapping("/{id}") public Plan actualizar(@PathVariable Long id, @RequestBody Plan entrada) {
        Plan plan = planes.findById(id).orElseThrow();
        plan.setNombre(entrada.getNombre()); plan.setDescripcion(entrada.getDescripcion());
        plan.setPrecio(entrada.getPrecio()); plan.setDuracionDias(entrada.getDuracionDias());
        return planes.save(plan);
    }
}
