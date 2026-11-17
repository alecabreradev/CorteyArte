import { Link } from "react-router-dom";
import SectionHeader from "../components/SectionHeader.jsx";
import Icon from "../components/Icon.jsx";
import { SALONS } from "../data/salons.js";
import { CONTACT, OPENING_HOURS, SERVICES } from "../data/business.js";

export default function Home() {
  return (
    <>
      <section className="hero">
        <h1>Corte &amp; Arte</h1>
        <p>Una experiencia de belleza y creatividad para un look perfecto. Elige tu sala y reserva tu cita en un minuto.</p>
        <Link to="/reservas" className="button">Reservar cita</Link>
      </section>

      <section className="section">
        <SectionHeader title="Galería" subtitle="Conoce nuestras instalaciones" />
        <div className="gallery-grid">
          {SALONS.map((s) => (
            <Link key={s.id} to={`/peluqueria#sala-${s.id}`} className="gallery-item">
              <img src={s.image} alt={s.name} loading="lazy" />
              <span className="gallery-caption">{s.name}</span>
              <span className="gallery-overlay">Ver sala</span>
            </Link>
          ))}
        </div>
      </section>

      <section className="section">
        <SectionHeader title="Servicios" subtitle="Lo que podemos hacer por ti" />
        <ul className="services-grid">
          {SERVICES.map((s) => (
            <li key={s.name} className="service-item">
              <img src={s.image} alt="" loading="lazy" />
              <span className="service-caption">{s.name}</span>
            </li>
          ))}
        </ul>
      </section>

      <section className="section">
        <SectionHeader title="Dónde estamos" subtitle="Estamos para atenderte" />
        <div className="contact-cards">
          <div className="card contact-card">
            <Icon name="location-outline" />
            <p>{CONTACT.address}</p>
          </div>
          <div className="card contact-card">
            <Icon name="time-outline" />
            <p>{OPENING_HOURS[0].days}: {OPENING_HOURS[0].hours}</p>
          </div>
          <div className="card contact-card">
            <Icon name="call-outline" />
            <p>{CONTACT.phone}</p>
          </div>
          <div className="card contact-card">
            <Icon name="mail-outline" />
            <p>{CONTACT.email}</p>
          </div>
        </div>
      </section>
    </>
  );
}
