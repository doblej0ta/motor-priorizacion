package com.priorizacion.controller;

import com.priorizacion.model.TipoSolicitud;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SolicitudRequest(
        @NotNull TipoSolicitud tipo,
        @NotNull @Min(1) @Max(5) Integer prioridadManual,
        @NotBlank String usuario
) {
}
