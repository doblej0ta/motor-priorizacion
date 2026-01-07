package com.priorizacion.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class GeneradorIdSolicitud {

    private final Map<TipoSolicitud, AtomicInteger> secuencias = new EnumMap<>(TipoSolicitud.class);

    public String generar(TipoSolicitud tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de solicitud es obligatorio para generar un ID");
        }
        AtomicInteger contador = secuencias.computeIfAbsent(tipo, key -> new AtomicInteger(0));
        int siguiente = contador.incrementAndGet();
        // Formato: PREFIJO-### (ej. INC-001)
        return String.format("%s-%03d", tipo.getPrefijo(), siguiente);
    }
}
