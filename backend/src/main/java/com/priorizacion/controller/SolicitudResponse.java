package com.priorizacion.controller;

import com.priorizacion.model.EstadoSolicitud;
import com.priorizacion.model.TipoSolicitud;
import java.time.LocalDateTime;

public record SolicitudResponse(
        Long id,
        String solicitudId,
        TipoSolicitud tipo,
        Integer prioridadManual,
        String usuario,
        LocalDateTime fechaCreacion,
        double prioridadCalculada,
        EstadoSolicitud estado
) {
}
