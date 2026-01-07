package com.priorizacion.controller;

import com.priorizacion.model.EstadoSolicitud;
import jakarta.validation.constraints.NotNull;

public record CambioEstadoRequest(
        @NotNull(message = "El estado es obligatorio")
        EstadoSolicitud estado
) {
}
