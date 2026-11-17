import { useState } from "react";
import { Link, useSearchParams } from "react-router-dom";
import SectionHeader from "../components/SectionHeader.jsx";
import BookingForm from "../components/BookingForm.jsx";
import Icon from "../components/Icon.jsx";
import { createReserva, reservaId } from "../api/reservas.js";
import { salonById } from "../data/salons.js";
import { CONTACT } from "../data/business.js";
import { EMPTY_BOOKING } from "../utils/validation.js";

export default function Booking() {
  const [params] = useSearchParams();
  const preselected = salonById(params.get("sala"))?.name ?? "";
  const [status, setStatus] = useState(null); // { type: "ok" | "error", text }
  const [formKey, setFormKey] = useState(0);

  const handleSubmit = async (values) => {
    setStatus(null);
    try {
      const created = await createReserva(values);
      const id = created && reservaId(created);
      setStatus({
        type: "ok",
        text: `Reserva confirmada${id ? ` (n.º ${id})` : ""} para el ${values.fecha} a las ${values.horaInicio}.`,
      });
      setFormKey((k) => k + 1); // reset the form
    } catch (err) {
      setStatus({ type: "error", text: err.message });
    }
  };

  return (
    <>
      <SectionHeader
        as="h1"
        title="Reserva tu cita"
        subtitle="Elige la sala, la fecha y la hora que mejor te vengan"
      />

      <div className="booking-layout">
        <div className="card">
          {status && (
            <div className={`notice notice-${status.type}`} role="status">
              <Icon name={status.type === "ok" ? "checkmark-circle" : "alert-circle"} />
              <p>{status.text}</p>
            </div>
          )}
          <BookingForm
            key={formKey}
            initial={{ ...EMPTY_BOOKING, sala: preselected }}
            onSubmit={handleSubmit}
            submitLabel="Confirmar reserva"
          />
        </div>

        <aside className="booking-info">
          <div className="card">
            <h2 className="card-title"><Icon name="information-circle-outline" /> Información importante</h2>
            <ul className="check-list single">
              <li><Icon name="checkmark" /> Llega 5-10 minutos antes de tu cita.</li>
              <li><Icon name="checkmark" /> Si necesitas cancelar, avísanos con 24 horas de antelación.</li>
              <li><Icon name="checkmark" /> Puedes pagar en efectivo o con tarjeta.</li>
            </ul>
          </div>
          <div className="card">
            <h2 className="card-title"><Icon name="call-outline" /> ¿Necesitas ayuda?</h2>
            <p className="big-phone">{CONTACT.phone}</p>
            <p className="muted">Lunes a viernes de 9:00 a 20:00</p>
          </div>
          <div className="card">
            <h2 className="card-title"><Icon name="cut-outline" /> ¿Dudas sobre las salas?</h2>
            <p className="muted">Mira qué ofrece cada una antes de reservar.</p>
            <Link to="/peluqueria" className="text-link">Ver salas</Link>
          </div>
        </aside>
      </div>
    </>
  );
}
