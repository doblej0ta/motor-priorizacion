import axios from "axios";

const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL || "/api",
});

export async function crearSolicitud(payload) {
  try {
    const response = await api.post("/solicitudes", payload);
    // Se retorna solo el cuerpo útil para el componente
    return response.data;
  } catch (error) {
    throw parseError(error);
  }
}

export async function obtenerTodas() {
  try {
    const response = await api.get("/solicitudes");
    return response.data;
  } catch (error) {
    throw parseError(error);
  }
}

export async function obtenerPriorizadas() {
  try {
    const response = await api.get("/solicitudes/priorizadas");
    return response.data;
  } catch (error) {
    throw parseError(error);
  }
}

export async function actualizarEstado(id, nuevoEstado) {
  try {
    const response = await api.patch(`/solicitudes/${id}/estado`, {
      estado: nuevoEstado,
    });
    return response.data;
  } catch (error) {
    throw parseError(error);
  }
}

function parseError(error) {
  if (error.response && error.response.data) {
    return new Error(error.response.data.message || "Error en la solicitud");
  }
  return new Error("No se pudo conectar con el servicio");
}
