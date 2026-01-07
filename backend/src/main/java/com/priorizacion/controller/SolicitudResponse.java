package com.priorizacion.controller;

import com.priorizacion.model.TipoSolicitud;
import java.time.LocalDateTime;

public record SolicitudResponse(
        String solicitudId,
        TipoSolicitud tipo,
        Integer prioridadManual,
        String usuario,
        LocalDateTime fechaCreacion,
        double prioridadCalculada
) {
}
