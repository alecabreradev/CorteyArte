import { Link } from "react-router-dom";

export default function NotFound() {
  return (
    <section className="empty-state">
      <h1>Página no encontrada</h1>
      <p>La dirección que has abierto no existe.</p>
      <Link to="/" className="button">Volver al inicio</Link>
    </section>
  );
}
