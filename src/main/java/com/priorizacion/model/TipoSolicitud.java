package com.priorizacion.model;

public enum TipoSolicitud {
    INCIDENTE("INC"),
    REQUERIMIENTO("REQ"),
    CONSULTA("CON");

    private final String prefijo;

    TipoSolicitud(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getPrefijo() {
        return prefijo;
    }
}
