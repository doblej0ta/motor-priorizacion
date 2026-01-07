import React, { useState } from "react";

const tipos = ["INCIDENTE", "REQUERIMIENTO", "CONSULTA"];
const prioridades = [1, 2, 3, 4, 5];

function FormularioSolicitud({ onCrear, loading }) {
  const [tipo, setTipo] = useState(tipos[0]);
  const [prioridadManual, setPrioridadManual] = useState(prioridades[0]);
  const [usuario, setUsuario] = useState("");
  const [mensaje, setMensaje] = useState(null);
  const [error, setError] = useState(null);

  async function manejarSubmit(event) {
    event.preventDefault();
    setMensaje(null);
    setError(null);
    // Validaciones básicas en cliente para evitar envíos vacíos
    if (!usuario.trim()) {
      setError("El usuario es obligatorio");
      return;
    }
    try {
      await onCrear({ tipo, prioridadManual: Number(prioridadManual), usuario: usuario.trim() });
      setMensaje("Solicitud creada correctamente");
      limpiarFormulario();
    } catch (err) {
      setError(err.message || "Error al crear solicitud");
    }
  }

  function limpiarFormulario() {
    setTipo(tipos[0]);
    setPrioridadManual(prioridades[0]);
    setUsuario("");
  }

  return (
    <form onSubmit={manejarSubmit}>
      <label>
        Tipo
        <select value={tipo} onChange={(e) => setTipo(e.target.value)} disabled={loading}>
          {tipos.map((opcion) => (
            <option key={opcion} value={opcion}>
              {opcion}
            </option>
          ))}
        </select>
      </label>

      <label>
        Prioridad Manual
        <select
          value={prioridadManual}
          onChange={(e) => setPrioridadManual(e.target.value)}
          disabled={loading}
        >
          {prioridades.map((valor) => (
            <option key={valor} value={valor}>
              {valor}
            </option>
          ))}
        </select>
      </label>

      <label>
        Usuario
        <input
          type="text"
          value={usuario}
          onChange={(e) => setUsuario(e.target.value)}
          placeholder="usuario"
          disabled={loading}
        />
      </label>

      <button type="submit" disabled={loading}>
        {loading ? "Creando..." : "Crear Solicitud"}
      </button>

      {mensaje && <div className="mensaje" style={{ color: "green" }}>{mensaje}</div>}
      {error && <div className="mensaje" style={{ color: "red" }}>{error}</div>}
    </form>
  );
}

export default FormularioSolicitud;
