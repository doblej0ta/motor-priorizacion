package com.priorizacion.service;

import com.priorizacion.model.Solicitud;

public record SolicitudPrioritaria(Solicitud solicitud, double prioridadCalculada) {
}
