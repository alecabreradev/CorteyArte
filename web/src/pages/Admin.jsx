import { useCallback, useEffect, useState } from "react";
import SectionHeader from "../components/SectionHeader.jsx";
import BookingForm from "../components/BookingForm.jsx";
import Icon from "../components/Icon.jsx";
import { deleteReserva, getReserva, getReservas, reservaId, updateReserva } from "../api/reservas.js";
import { EMPTY_BOOKING } from "../utils/validation.js";

/** Turns an API booking into form values (dates may come as full ISO strings). */
function toForm(r) {
  return {
    ...EMPTY_BOOKING,
    ...r,
    fecha: String(r.fecha ?? "").slice(0, 10),
    numeroPersonas: r.numeroPersonas ?? 1,
    comentario: r.comentario ?? "",
  };
}

function ReservaCard({ reserva, onEdit, onDeleted }) {
  const id = reservaId(reserva);
  const [confirming, setConfirming] = useState(false);
  const [error, setError] = useState("");

  const remove = async () => {
    try {
      await deleteReserva(id);
      onDeleted(id);
    } catch (err) {
      setError(err.message);
      setConfirming(false);
    }
  };

  return (
    <li className="card reserva">
      <div className="reserva-head">
        <h3>Reserva #{id}</h3>
        <span className="tag">{reserva.sala}</span>
      </div>
      <dl className="reserva-data">
        <div><dt>Cliente</dt><dd>{reserva.nickname}</dd></div>
        <div><dt>Fecha</dt><dd>{String(reserva.fecha).slice(0, 10)}</dd></div>
        <div><dt>Hora</dt><dd>{reserva.horaInicio}{reserva.horaFin ? `–${reserva.horaFin}` : ""}</dd></div>
        <div><dt>Personas</dt><dd>{reserva.numeroPersonas ?? 1}</dd></div>
        <div><dt>Teléfono</dt><dd>{reserva.telefono}</dd></div>
        <div><dt>Email</dt><dd>{reserva.email}</dd></div>
        {reserva.comentario && <div className="wide"><dt>Notas</dt><dd>{reserva.comentario}</dd></div>}
      </dl>
      {error && <p className="field-error" role="alert">{error}</p>}
      <div className="reserva-actions">
        {confirming ? (
          <>
            <span className="muted">¿Eliminar esta reserva?</span>
            <button className="button button-ghost" onClick={() => setConfirming(false)}>Cancelar</button>
            <button className="button button-danger" onClick={remove}>Sí, eliminar</button>
          </>
        ) : (
          <>
            <button className="button button-ghost" onClick={() => onEdit(reserva)}>
              <Icon name="create-outline" /> Editar
            </button>
            <button className="button button-ghost" onClick={() => setConfirming(true)}>
              <Icon name="trash-outline" /> Eliminar
            </button>
          </>
        )}
      </div>
    </li>
  );
}

export default function Admin() {
  const [tab, setTab] = useState("list");
  const [reservas, setReservas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [editing, setEditing] = useState(null);
  const [notice, setNotice] = useState("");
  const [searchId, setSearchId] = useState("");
  const [found, setFound] = useState(null); // { reserva } | { error }

  const load = useCallback(async () => {
    setLoading(true);
    setError("");
    try {
      const data = await getReservas();
      setReservas(Array.isArray(data) ? data : data?.reservas ?? []);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    load();
  }, [load]);

  const handleSave = async (values) => {
    const id = reservaId(editing);
    try {
      await updateReserva(id, values);
      setNotice(`Reserva #${id} actualizada.`);
      setEditing(null);
      setFound(null);
      load();
    } catch (err) {
      setNotice(`No se pudo guardar: ${err.message}`);
    }
  };

  const handleDeleted = (id) => {
    setReservas((list) => list.filter((r) => reservaId(r) !== id));
    setFound(null);
    setNotice(`Reserva #${id} eliminada.`);
  };

  const search = async (e) => {
    e.preventDefault();
    if (!searchId.trim()) return;
    try {
      setFound({ reserva: await getReserva(searchId.trim()) });
    } catch (err) {
      setFound({ error: err.status === 404 ? "No existe ninguna reserva con ese número." : err.message });
    }
  };

  if (editing) {
    return (
      <>
        <SectionHeader as="h1" title={`Editar reserva #${reservaId(editing)}`} subtitle="Cambia los datos y guarda" />
        <div className="card">
          <BookingForm
            idPrefix="edit"
            editing
            initial={toForm(editing)}
            onSubmit={handleSave}
            onCancel={() => setEditing(null)}
            submitLabel="Guardar cambios"
          />
        </div>
      </>
    );
  }

  return (
    <>
      <SectionHeader as="h1" title="Panel de administración" subtitle="Gestiona las reservas de la peluquería" />

      {notice && (
        <div className="notice notice-ok" role="status">
          <Icon name="checkmark-circle" />
          <p>{notice}</p>
          <button className="icon-button" onClick={() => setNotice("")} aria-label="Cerrar aviso">
            <Icon name="close-outline" />
          </button>
        </div>
      )}

      <div className="tabs" role="tablist">
        <button role="tab" aria-selected={tab === "list"} onClick={() => setTab("list")}>
          Todas las reservas {!loading && !error && <span className="count">{reservas.length}</span>}
        </button>
        <button role="tab" aria-selected={tab === "search"} onClick={() => setTab("search")}>
          Buscar por número
        </button>
      </div>

      {tab === "list" && (
        <section aria-live="polite">
          {loading && <p className="muted">Cargando reservas…</p>}
          {error && (
            <div className="notice notice-error" role="alert">
              <Icon name="alert-circle" />
              <p>{error}</p>
              <button className="button button-ghost" onClick={load}>Reintentar</button>
            </div>
          )}
          {!loading && !error && reservas.length === 0 && (
            <p className="empty-state">Todavía no hay reservas. Las que se hagan desde la web o la app aparecerán aquí.</p>
          )}
          <ul className="reservas">
            {reservas.map((r) => (
              <ReservaCard key={reservaId(r)} reserva={r} onEdit={setEditing} onDeleted={handleDeleted} />
            ))}
          </ul>
        </section>
      )}

      {tab === "search" && (
        <section>
          <form className="search-form" onSubmit={search}>
            <label htmlFor="search-id">Número de reserva</label>
            <div className="search-row">
              <input id="search-id" value={searchId} onChange={(e) => setSearchId(e.target.value)} placeholder="Por ejemplo, 1" />
              <button className="button" type="submit"><Icon name="search-outline" /> Buscar</button>
            </div>
          </form>
          {found?.error && <p className="field-error" role="alert">{found.error}</p>}
          {found?.reserva && (
            <ul className="reservas">
              <ReservaCard reserva={found.reserva} onEdit={setEditing} onDeleted={handleDeleted} />
            </ul>
          )}
        </section>
      )}
    </>
  );
}
