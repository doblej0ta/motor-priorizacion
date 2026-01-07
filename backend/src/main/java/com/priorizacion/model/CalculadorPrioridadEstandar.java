package com.priorizacion.model;

import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class CalculadorPrioridadEstandar implements EstrategiaPriorizacion {

    @Override
    public double calcularPrioridad(Solicitud solicitud) {
        double multiplicadorTipo = obtenerMultiplicadorTipo(solicitud.getTipo());
        double antiguedad = calcularAntiguedad(solicitud.getFechaCreacion());
        // Formula: prioridad manual ponderada por tipo + bono por antiguedad
        return (solicitud.getPrioridadManual() * multiplicadorTipo) + antiguedad;
    }

    private double obtenerMultiplicadorTipo(TipoSolicitud tipo) {
        return switch (tipo) {
            case INCIDENTE -> 1.5;
            case REQUERIMIENTO -> 1.0;
            case CONSULTA -> 0.8;
        };
    }

    private double calcularAntiguedad(LocalDateTime fechaCreacion) {
        long dias = Duration.between(fechaCreacion, LocalDateTime.now()).toDays();
        return dias * 0.1;
    }
}
