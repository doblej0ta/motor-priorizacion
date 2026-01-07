import { useEffect, useState } from "react";
import {
  crearSolicitud,
  obtenerTodas,
  obtenerPriorizadas,
} from "../services/solicitudService";

export default function useSolicitudes() {
  const [solicitudes, setSolicitudes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Ciclo de carga inicial: trae todas las solicitudes ordenadas por prioridad calculada
  useEffect(() => {
    cargarPriorizadas();
  }, []);

  async function crear(solicitud) {
    setLoading(true);
    setError(null);
    try {
      const creada = await crearSolicitud(solicitud);
      // Recargar las solicitudes ordenadas por prioridad
      await cargarPriorizadas();
      return creada;
    } catch (err) {
      setError(err.message);
      throw err;
    } finally {
      setLoading(false);
    }
  }

  async function cargarTodas() {
    setLoading(true);
    setError(null);
    try {
      const data = await obtenerTodas();
      setSolicitudes(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  async function cargarPriorizadas() {
    setLoading(true);
    setError(null);
    try {
      const data = await obtenerPriorizadas();
      setSolicitudes(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  return {
    solicitudes,
    loading,
    error,
    crear,
    cargarTodas,
    cargarPriorizadas,
  };
}
