import { Link } from "react-router-dom";
import Icon from "./Icon.jsx";
import useTheme from "../hooks/useTheme.js";

export default function Header({ onMenuClick }) {
  const { theme, toggleTheme } = useTheme();
  const isDark = theme === "dark";

  return (
    <header className="topbar">
      <button className="icon-button menu-button" onClick={onMenuClick} aria-label="Abrir menú">
        <Icon name="menu-outline" />
      </button>
      <div className="topbar-actions">
        <button
          className="icon-button"
          onClick={toggleTheme}
          aria-label={isDark ? "Cambiar a modo claro" : "Cambiar a modo oscuro"}
        >
          <Icon name={isDark ? "sunny-outline" : "moon-outline"} />
        </button>
        <Link to="/reservas" className="button">
          <Icon name="calendar-outline" />
          Reservar
        </Link>
      </div>
    </header>
  );
}
