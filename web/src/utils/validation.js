export const EMPTY_BOOKING = {
  nickname: "",
  email: "",
  telefono: "",
  sala: "",
  fecha: "",
  horaInicio: "",
  horaFin: "",
  numeroPersonas: 1,
  comentario: "",
};

export function todayISO() {
  const d = new Date();
  const pad = (n) => String(n).padStart(2, "0");
  return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}`;
}

/** Adds one hour to "HH:MM" (used as the default end time). */
export function plusOneHour(time) {
  if (!time) return "";
  const [h, m] = time.split(":").map(Number);
  return `${String(Math.min(h + 1, 23)).padStart(2, "0")}:${String(m).padStart(2, "0")}`;
}

/**
 * Validates a booking and returns an object { field: message } with the errors found.
 * Same rules as the original web form and the Android app.
 */
export function validateBooking(b, { allowPastDates = false } = {}) {
  const errors = {};

  if (!b.nickname.trim()) errors.nickname = "Introduce tu nombre.";
  else if (!/^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ ]+$/.test(b.nickname.trim()))
    errors.nickname = "El nombre solo puede contener letras y espacios.";

  if (!b.email.trim()) errors.email = "Introduce tu correo electrónico.";
  else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(b.email.trim()))
    errors.email = "Escribe un correo con el formato nombre@dominio.com.";

  if (!/^\d{9}$/.test(b.telefono.trim())) errors.telefono = "El teléfono debe tener 9 dígitos.";

  if (!b.sala) errors.sala = "Elige una sala.";

  if (!b.fecha) errors.fecha = "Elige una fecha.";
  else if (!allowPastDates && b.fecha < todayISO()) errors.fecha = "La fecha no puede ser anterior a hoy.";

  if (!b.horaInicio) errors.horaInicio = "Elige una hora.";
  if (b.horaInicio && b.horaFin && b.horaFin <= b.horaInicio)
    errors.horaFin = "La hora de fin debe ser posterior a la de inicio.";

  const n = Number(b.numeroPersonas);
  if (!Number.isInteger(n) || n < 1 || n > 4) errors.numeroPersonas = "Entre 1 y 4 personas.";

  return errors;
}
