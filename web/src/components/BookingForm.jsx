import { useState } from "react";
import Icon from "./Icon.jsx";
import { SALONS } from "../data/salons.js";
import { BOOKING_HOURS } from "../data/business.js";
import { plusOneHour, todayISO, validateBooking } from "../utils/validation.js";

function Field({ id, label, error, full, children }) {
  return (
    <div className={`form-group${full ? " full-width" : ""}`}>
      <label htmlFor={id}>{label}</label>
      {children}
      {error && (
        <p className="field-error" id={`${id}-error`} role="alert">
          {error}
        </p>
      )}
    </div>
  );
}

/**
 * Booking form used to create a booking (Reservas page) and to edit one (Admin page).
 * `onSubmit` receives the validated values and returns a promise.
 */
export default function BookingForm({ initial, onSubmit, submitLabel, onCancel, idPrefix = "f", editing = false }) {
  const [values, setValues] = useState(initial);
  const [errors, setErrors] = useState({});
  const [sending, setSending] = useState(false);

  const set = (name) => (e) => {
    const value = e.target.value;
    setValues((v) => {
      const next = { ...v, [name]: value };
      // Default the end time to one hour after the start time
      if (name === "horaInicio" && (!v.horaFin || v.horaFin <= value)) next.horaFin = plusOneHour(value);
      return next;
    });
    if (errors[name]) setErrors((err) => ({ ...err, [name]: undefined }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const found = validateBooking(values, { allowPastDates: editing });
    setErrors(found);
    if (Object.keys(found).length) {
      document.getElementById(`${idPrefix}-${Object.keys(found)[0]}`)?.focus();
      return;
    }
    setSending(true);
    try {
      await onSubmit(values);
    } finally {
      setSending(false);
    }
  };

  const fieldProps = (name) => ({
    id: `${idPrefix}-${name}`,
    name,
    value: values[name],
    onChange: set(name),
    "aria-invalid": errors[name] ? "true" : undefined,
    "aria-describedby": errors[name] ? `${idPrefix}-${name}-error` : undefined,
  });

  return (
    <form className="booking-form" onSubmit={handleSubmit} noValidate>
      <Field id={`${idPrefix}-nickname`} label="Nombre completo" error={errors.nickname}>
        <input type="text" autoComplete="name" placeholder="Tu nombre y apellidos" {...fieldProps("nickname")} />
      </Field>
      <Field id={`${idPrefix}-email`} label="Correo electrónico" error={errors.email}>
        <input type="email" autoComplete="email" placeholder="ejemplo@correo.com" {...fieldProps("email")} />
      </Field>
      <Field id={`${idPrefix}-telefono`} label="Teléfono" error={errors.telefono}>
        <input type="tel" inputMode="numeric" autoComplete="tel" placeholder="600123456" {...fieldProps("telefono")} />
      </Field>
      <Field id={`${idPrefix}-sala`} label="Sala" error={errors.sala}>
        <select {...fieldProps("sala")}>
          <option value="">Elige una sala</option>
          {SALONS.map((s) => (
            <option key={s.id} value={s.name}>{s.name}</option>
          ))}
        </select>
      </Field>
      <Field id={`${idPrefix}-fecha`} label="Fecha" error={errors.fecha}>
        <input type="date" min={editing ? undefined : todayISO()} {...fieldProps("fecha")} />
      </Field>
      <Field id={`${idPrefix}-horaInicio`} label="Hora" error={errors.horaInicio}>
        <select {...fieldProps("horaInicio")}>
          <option value="">Elige una hora</option>
          {BOOKING_HOURS.map((h) => (
            <option key={h} value={h}>{h}</option>
          ))}
        </select>
      </Field>
      <Field id={`${idPrefix}-horaFin`} label="Hora de fin" error={errors.horaFin}>
        <input type="time" {...fieldProps("horaFin")} />
      </Field>
      <Field id={`${idPrefix}-numeroPersonas`} label="Número de personas" error={errors.numeroPersonas}>
        <input type="number" min="1" max="4" {...fieldProps("numeroPersonas")} />
      </Field>
      <Field id={`${idPrefix}-comentario`} label="Notas adicionales" full>
        <textarea placeholder="Cuéntanos cualquier detalle sobre tu reserva" {...fieldProps("comentario")} />
      </Field>
      <div className="form-actions">
        {onCancel && (
          <button type="button" className="button button-ghost" onClick={onCancel}>Cancelar</button>
        )}
        <button type="submit" className="button" disabled={sending}>
          <Icon name="calendar-outline" />
          {sending ? "Enviando…" : submitLabel}
        </button>
      </div>
    </form>
  );
}
