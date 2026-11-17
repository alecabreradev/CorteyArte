/**
 * Client for the reservations REST API (Node.js + Express + MongoDB, folder ../api).
 * The same API is used by the Android app.
 *
 * Endpoints:
 *   GET    /api/reservas        list all bookings
 *   GET    /api/reservas/:id    get one booking
 *   POST   /api/reservas        create a booking
 *   PUT    /api/reservas/:id    update a booking
 *   DELETE /api/reservas/:id    delete a booking
 */
const API_URL = (import.meta.env.VITE_API_URL || "http://localhost:3000/api").replace(/\/$/, "");

async function request(path, options = {}) {
  let response;
  try {
    response = await fetch(`${API_URL}${path}`, {
      headers: { "Content-Type": "application/json" },
      ...options,
    });
  } catch {
    throw new Error("No se puede conectar con el servidor de reservas. Comprueba que la API está en marcha.");
  }

  const text = await response.text();
  const data = text ? safeJson(text) : null;

  if (!response.ok) {
    const message = data?.mensaje || data?.msg || data?.message || `Error ${response.status}`;
    const error = new Error(message);
    error.status = response.status;
    throw error;
  }
  return data;
}

function safeJson(text) {
  try {
    return JSON.parse(text);
  } catch {
    return { msg: text };
  }
}

/** Same field names as the Reserva model shared with the Android app. */
export function toApi(form) {
  return {
    nickname: form.nickname.trim(),
    email: form.email.trim(),
    telefono: form.telefono.trim(),
    sala: form.sala,
    fecha: form.fecha,
    horaInicio: form.horaInicio,
    horaFin: form.horaFin,
    numeroPersonas: Number(form.numeroPersonas) || 1,
    comentario: form.comentario?.trim() ?? "",
  };
}

/** Bookings may come with a numeric `id` or MongoDB's `_id`. */
export const reservaId = (reserva) => reserva.id ?? reserva._id;

export const getReservas = () => request("/reservas");
export const getReserva = (id) => request(`/reservas/${encodeURIComponent(id)}`);
export const createReserva = (form) =>
  request("/reservas", { method: "POST", body: JSON.stringify(toApi(form)) });
export const updateReserva = (id, form) =>
  request(`/reservas/${encodeURIComponent(id)}`, { method: "PUT", body: JSON.stringify(toApi(form)) });
export const deleteReserva = (id) =>
  request(`/reservas/${encodeURIComponent(id)}`, { method: "DELETE" });
