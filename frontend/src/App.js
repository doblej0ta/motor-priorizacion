import React from "react";
import FormularioSolicitud from "./components/FormularioSolicitud";
import ListaSolicitudes from "./components/ListaSolicitudes";
import useSolicitudes from "./hooks/useSolicitudes";

function App() {
  const {
    solicitudes,
    loading,
    error,
    crear,
    cargarTodas,
    cargarPriorizadas,
  } = useSolicitudes();

  return (
    <div className="app-container">
      <header>
        <h1>Motor de Priorización de Solicitudes</h1>
      </header>

      {/* Integración principal: formulario y tablas controlados por el hook */}
      <section className="panel">
        <FormularioSolicitud onCrear={crear} loading={loading} />
        <div className="acciones">
          <button onClick={cargarTodas} disabled={loading}>
            Ver Todas
          </button>
          <button onClick={cargarPriorizadas} disabled={loading}>
            Ver Priorizadas
          </button>
        </div>
      </section>

      <section className="panel">
        <h2>Solicitudes</h2>
        <ListaSolicitudes 
          solicitudes={solicitudes} 
          loading={loading} 
          error={error}
          onActualizacion={cargarTodas}
        />
      </section>
    </div>
  );
}

export default App;
