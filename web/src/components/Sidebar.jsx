import { NavLink } from "react-router-dom";
import Icon from "./Icon.jsx";

const LINKS = [
  { to: "/", label: "Inicio", icon: "home-outline", end: true },
  { to: "/peluqueria", label: "Peluquería", icon: "cut-outline" },
  { to: "/reservas", label: "Reservas", icon: "calendar-outline" },
  { to: "/equipo", label: "Equipo", icon: "people-outline" },
  { to: "/admin", label: "Admin", icon: "settings-outline" },
];

export default function Sidebar({ open, onClose }) {
  return (
    <>
      <aside className={`sidebar${open ? " is-open" : ""}`} aria-label="Navegación principal">
        <div className="sidebar-brand">
          <img src="/img/logo.png" alt="" width="40" height="40" />
          <span>Corte &amp; Arte</span>
          <button className="icon-button sidebar-close" onClick={onClose} aria-label="Cerrar menú">
            <Icon name="close-outline" />
          </button>
        </div>
        <nav>
          <ul className="sidebar-nav">
            {LINKS.map((link) => (
              <li key={link.to}>
                <NavLink to={link.to} end={link.end}>
                  <Icon name={link.icon} />
                  <span>{link.label}</span>
                </NavLink>
              </li>
            ))}
          </ul>
        </nav>
      </aside>
      {open && <div className="sidebar-backdrop" onClick={onClose} aria-hidden="true" />}
    </>
  );
}
