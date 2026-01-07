package com.priorizacion.controller;

import com.priorizacion.model.Solicitud;
import com.priorizacion.service.SolicitudPrioritaria;
import com.priorizacion.service.SolicitudService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping
    public ResponseEntity<SolicitudResponse> crear(@Valid @RequestBody SolicitudRequest request) {
        Solicitud solicitud = solicitudService.crearSolicitud(
                request.tipo(),
                request.prioridadManual(),
                request.usuario()
        );
        double prioridadCalculada = solicitudService.calcularPrioridad(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(solicitud, prioridadCalculada));
    }

    @GetMapping
    public List<SolicitudResponse> obtenerTodas() {
        return solicitudService.obtenerTodas().stream()
                .map(solicitud -> toResponse(solicitud, solicitudService.calcularPrioridad(solicitud)))
                .toList();
    }

    @GetMapping("/priorizadas")
    public List<SolicitudResponse> obtenerPriorizadas() {
        return solicitudService.obtenerPriorizadas().stream()
                .map(prioritaria -> toResponse(prioritaria.solicitud(), prioritaria.prioridadCalculada()))
                .toList();
    }

    private SolicitudResponse toResponse(Solicitud solicitud, double prioridadCalculada) {
        return new SolicitudResponse(
                solicitud.getSolicitudId(),
                solicitud.getTipo(),
                solicitud.getPrioridadManual(),
                solicitud.getUsuario(),
                solicitud.getFechaCreacion(),
                prioridadCalculada
        );
    }
}
