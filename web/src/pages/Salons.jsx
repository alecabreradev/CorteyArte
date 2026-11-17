import { Link } from "react-router-dom";
import SectionHeader from "../components/SectionHeader.jsx";
import Icon from "../components/Icon.jsx";
import Rating from "../components/Rating.jsx";
import CtaBanner from "../components/CtaBanner.jsx";
import { SALONS } from "../data/salons.js";
import { TESTIMONIALS } from "../data/team.js";

export default function Salons() {
  return (
    <>
      <SectionHeader as="h1" title="Salas especializadas" subtitle="Conoce nuestras instalaciones y servicios" />

      {SALONS.map((salon) => (
        <article key={salon.id} id={`sala-${salon.id}`} className={`salon card${salon.premium ? " salon-premium" : ""}`}>
          <div className="salon-image">
            <img src={salon.image} alt={`Foto de la ${salon.name}`} loading="lazy" />
          </div>
          <div className="salon-body">
            <div className="salon-title">
              <h2>{salon.name}</h2>
              {salon.tag && <span className={`tag${salon.premium ? " tag-premium" : ""}`}>{salon.tag}</span>}
            </div>
            <p>{salon.description}</p>
            <h3 className="underline-title">{salon.servicesTitle}</h3>
            <ul className="check-list">
              {salon.services.map((service) => (
                <li key={service}>
                  <Icon name={salon.premium ? "star" : "checkmark-circle"} />
                  {service}
                </li>
              ))}
            </ul>
            <Link to={`/reservas?sala=${salon.id}`} className="button">
              {salon.premium ? "Reservar experiencia VIP" : "Reservar esta sala"}
            </Link>
          </div>
        </article>
      ))}

      <section className="section">
        <SectionHeader title="Lo que dicen nuestros clientes" />
        <div className="testimonials">
          {TESTIMONIALS.map((t) => (
            <figure key={t.author} className="card testimonial">
              <Rating value={t.rating} />
              <blockquote>“{t.text}”</blockquote>
              <figcaption>
                <img src={t.image} alt="" />
                {t.author}
              </figcaption>
            </figure>
          ))}
        </div>
      </section>

      <CtaBanner text="Reserva ahora y obtén un 15 % de descuento en tu primer servicio." />
    </>
  );
}
