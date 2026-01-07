package com.priorizacion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solicitud_id", nullable = false, unique = true, updatable = false)
    private String solicitudId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSolicitud tipo;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "prioridad_manual", nullable = false)
    private Integer prioridadManual;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @NotBlank
    @Column(nullable = false)
    private String usuario;

    protected Solicitud() {
        // Constructor requerido por JPA
    }

    public Solicitud(String solicitudId, TipoSolicitud tipo, Integer prioridadManual, String usuario) {
        validarCampos(solicitudId, tipo, prioridadManual, usuario);
        this.solicitudId = solicitudId;
        this.tipo = tipo;
        this.prioridadManual = prioridadManual;
        this.usuario = usuario;
    }

    @PrePersist
    void asignarFechaCreacion() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }

    private void validarCampos(String solicitudId, TipoSolicitud tipo, Integer prioridadManual, String usuario) {
        if (solicitudId == null || solicitudId.isBlank()) {
            throw new IllegalArgumentException("El identificador de solicitud es obligatorio");
        }
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

    public Long getId() {
        return id;
    }

    public String getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(String solicitudId) {
        this.solicitudId = solicitudId;
    }

    public TipoSolicitud getTipo() {
        return tipo;
    }

    public void setTipo(TipoSolicitud tipo) {
        this.tipo = tipo;
    }

    public Integer getPrioridadManual() {
        return prioridadManual;
    }

    public void setPrioridadManual(Integer prioridadManual) {
        this.prioridadManual = prioridadManual;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    // La fecha de creación se asigna solo en @PrePersist

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
