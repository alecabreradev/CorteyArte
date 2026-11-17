import { Link } from "react-router-dom";

export default function CtaBanner({ title = "¿Listo para renovar tu imagen?", text }) {
  return (
    <section className="cta">
      <h2>{title}</h2>
      {text && <p>{text}</p>}
      <Link to="/reservas" className="button button-light">Reservar cita</Link>
    </section>
  );
}
