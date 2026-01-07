package com.priorizacion.service;

import com.priorizacion.model.EstrategiaPriorizacion;
import com.priorizacion.model.GeneradorIdSolicitud;
import com.priorizacion.model.Solicitud;
import com.priorizacion.model.TipoSolicitud;
import com.priorizacion.repository.SolicitudRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final GeneradorIdSolicitud generadorIdSolicitud;
    private final EstrategiaPriorizacion estrategiaPriorizacion;

    public SolicitudService(
            SolicitudRepository solicitudRepository,
            GeneradorIdSolicitud generadorIdSolicitud,
            EstrategiaPriorizacion estrategiaPriorizacion
    ) {
        this.solicitudRepository = solicitudRepository;
        this.generadorIdSolicitud = generadorIdSolicitud;
        this.estrategiaPriorizacion = estrategiaPriorizacion;
    }

    public Solicitud crearSolicitud(TipoSolicitud tipo, Integer prioridadManual, String usuario) {
        validarEntrada(tipo, prioridadManual, usuario);
        String solicitudId = generadorIdSolicitud.generar(tipo);
        Solicitud solicitud = new Solicitud(solicitudId, tipo, prioridadManual, usuario.trim());
        return solicitudRepository.save(solicitud);
    }

    public List<Solicitud> obtenerTodas() {
        return solicitudRepository.findAll();
    }

    public List<SolicitudPrioritaria> obtenerPriorizadas() {
        return solicitudRepository.findAll().stream()
                .map(this::crearPrioritaria)
                .sorted(Comparator.comparingDouble(SolicitudPrioritaria::prioridadCalculada).reversed())
                .toList();
    }

    public double calcularPrioridad(Solicitud solicitud) {
        return estrategiaPriorizacion.calcularPrioridad(solicitud);
    }

    private SolicitudPrioritaria crearPrioritaria(Solicitud solicitud) {
        return new SolicitudPrioritaria(solicitud, calcularPrioridad(solicitud));
    }

    private void validarEntrada(TipoSolicitud tipo, Integer prioridadManual, String usuario) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de solicitud es obligatorio");
        }
        if (prioridadManual == null || prioridadManual < 1 || prioridadManual > 5) {
            throw new IllegalArgumentException("La prioridad manual debe estar entre 1 y 5");
        }
        if (usuario == null || usuario.isBlank()) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
    }
}
