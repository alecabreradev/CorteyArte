import SectionHeader from "../components/SectionHeader.jsx";
import Icon from "../components/Icon.jsx";
import Rating from "../components/Rating.jsx";
import CtaBanner from "../components/CtaBanner.jsx";
import { AWARDS, STYLISTS, TESTIMONIALS, VALUES } from "../data/team.js";

export default function Team() {
  return (
    <>
      <SectionHeader as="h1" title="Nuestro equipo" subtitle="El talento detrás de cada creación" />

      <section className="section">
        <SectionHeader title="Nuestra filosofía" subtitle="Los valores que nos definen" />
        <div className="values-grid">
          {VALUES.map((v) => (
            <div key={v.title} className="card value-card">
              <Icon name={v.icon} />
              <h3>{v.title}</h3>
              <p>{v.text}</p>
            </div>
          ))}
        </div>
      </section>

      <section className="section">
        <SectionHeader title="Conoce a nuestros estilistas" />
        <div className="team-grid">
          {STYLISTS.map((m) => (
            <article key={m.name} className="card member">
              <img src={m.image} alt={m.name} loading="lazy" />
              <div className="member-body">
                <h3>{m.name}</h3>
                <p className="member-role">{m.role}</p>
                <p>{m.bio}</p>
                <ul className="tags">
                  {m.skills.map((s) => <li key={s}>{s}</li>)}
                </ul>
              </div>
            </article>
          ))}
        </div>
      </section>

      <section className="section">
        <SectionHeader title="Reseñas" subtitle="La opinión de nuestros clientes" />
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

      <section className="section">
        <SectionHeader title="Reconocimientos" subtitle="Premios que avalan nuestra calidad" />
        <ol className="awards">
          {AWARDS.map((a) => (
            <li key={a.title} className="card award">
              <span className="award-icon"><Icon name={a.icon} /></span>
              <span className="award-year">{a.year}</span>
              <h3>{a.title}</h3>
              <p className="muted">{a.by}</p>
            </li>
          ))}
        </ol>
      </section>

      <CtaBanner text="Nuestro equipo te está esperando." />
    </>
  );
}
