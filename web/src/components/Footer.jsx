import Icon from "./Icon.jsx";
import { CONTACT, OPENING_HOURS, SOCIAL } from "../data/business.js";

export default function Footer() {
  return (
    <footer className="footer">
      <div className="footer-grid">
        <div>
          <p className="footer-brand">
            <Icon name="cut-outline" /> Corte &amp; Arte
          </p>
          <p className="muted">Tu salón de belleza de confianza desde 2010</p>
        </div>
        <div>
          <h2 className="footer-title">Contacto</h2>
          <p><Icon name="location-outline" /> {CONTACT.address}</p>
          <p><Icon name="call-outline" /> {CONTACT.phone}</p>
          <p><Icon name="mail-outline" /> {CONTACT.email}</p>
        </div>
        <div>
          <h2 className="footer-title">Horario</h2>
          {OPENING_HOURS.map((row) => (
            <p key={row.days}>{row.days}: {row.hours}</p>
          ))}
        </div>
        <div className="social-links">
          {SOCIAL.map((s) => (
            <a key={s.name} href={s.url} aria-label={s.name} target="_blank" rel="noopener noreferrer">
              <Icon name={s.icon} />
            </a>
          ))}
        </div>
      </div>
      <p className="footer-bottom">© {new Date().getFullYear()} Corte &amp; Arte</p>
    </footer>
  );
}
