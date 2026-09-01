package com.gymfit.controller;

import jakarta.validation.constraints.*;

public record PedidoCrearRequest(
        @NotBlank String clienteNombre,
        @NotBlank String whatsapp,
        String objetivo,
        @NotNull Long productoId,
        @NotNull @Min(1) Integer cantidad) {
}
