import React from "react";
import { actualizarEstado } from "../services/solicitudService";

function ListaSolicitudes({ solicitudes, loading, error, onActualizacion }) {
  function badgeClass(prioridad) {
    if (prioridad > 4) return "badge badge-alta";
    if (prioridad >= 2) return "badge badge-media";
    return "badge badge-baja";
  }

  function estadoBadgeClass(estado) {
    if (estado === "NUEVO") return "badge-estado badge-nuevo";
    if (estado === "EN_PROGRESO") return "badge-estado badge-progreso";
    if (estado === "COMPLETADO") return "badge-estado badge-completado";
    return "badge-estado";
  }

  async function cambiarEstado(id, nuevoEstado) {
    try {
      await actualizarEstado(id, nuevoEstado);
      if (onActualizacion) {
        onActualizacion();
      }
    } catch (err) {
      alert("Error al cambiar estado: " + err.message);
    }
  }

  // Código de colores: baja gris, media amarilla, alta roja
  if (loading) {
    return <div className="estado">Cargando...</div>;
  }

  if (error) {
    return <div className="estado" style={{ color: "red" }}>{error}</div>;
  }

  if (!solicitudes || solicitudes.length === 0) {
    return <div className="estado">No hay solicitudes</div>;
  }

  return (
    <table className="tabla-solicitudes">
      <thead>
        <tr>
          <th>ID</th>
          <th>Tipo</th>
          <th>Prioridad Manual</th>
          <th>Usuario</th>
          <th>Fecha</th>
          <th>Prioridad Calculada</th>
          <th>Estado</th>
        </tr>
      </thead>
      <tbody>
        {solicitudes.map((solicitud) => (
          <tr key={solicitud.solicitudId}>
            <td>{solicitud.solicitudId}</td>
            <td>{solicitud.tipo}</td>
            <td>{solicitud.prioridadManual}</td>
            <td>{solicitud.usuario}</td>
            <td>{new Date(solicitud.fechaCreacion).toLocaleString()}</td>
            <td>
              <span className={badgeClass(solicitud.prioridadCalculada)}>
                {solicitud.prioridadCalculada.toFixed(2)}
              </span>
            </td>
            <td>
              <select
                className="select-estado"
                value={solicitud.estado}
                onChange={(e) => cambiarEstado(solicitud.solicitudId.split('-')[1], e.target.value)}
              >
                <option value="NUEVO">NUEVO</option>
                <option value="EN_PROGRESO">EN_PROGRESO</option>
                <option value="COMPLETADO">COMPLETADO</option>
              </select>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default ListaSolicitudes;
