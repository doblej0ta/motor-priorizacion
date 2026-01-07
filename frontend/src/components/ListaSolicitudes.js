import React from "react";

function ListaSolicitudes({ solicitudes, loading, error }) {
  function badgeClass(prioridad) {
    if (prioridad > 4) return "badge badge-alta";
    if (prioridad >= 2) return "badge badge-media";
    return "badge badge-baja";
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
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default ListaSolicitudes;
