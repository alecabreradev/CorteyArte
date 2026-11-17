const express = require("express");
const Reserva = require("../models/reserva");
const getNextSequence = require("../helpers/getNextSequence");

const router = express.Router();

const FIELDS = [
  "sala", "nickname", "carnet", "telefono", "email",
  "fecha", "horaInicio", "horaFin", "numeroPersonas", "comentario",
];

/** Keeps only the known booking fields from the request body. */
function pick(body, { partial = false } = {}) {
  const data = {};
  for (const field of FIELDS) {
    if (body[field] !== undefined) data[field] = body[field];
    else if (!partial) data[field] = undefined;
  }
  return data;
}

function parseId(req, res) {
  const id = Number.parseInt(req.params.id, 10);
  if (Number.isNaN(id)) {
    res.status(400).json({ mensaje: "El id debe ser un número" });
    return null;
  }
  return id;
}

// Wraps async handlers so any error returns a 500 instead of crashing the server
const handle = (fn) => (req, res) =>
  fn(req, res).catch((err) => {
    console.error(err);
    res.status(500).json({ mensaje: "Error interno del servidor" });
  });

// GET all bookings
router.get("/reservas", handle(async (req, res) => {
  const reservas = await Reserva.find().sort({ id: 1 });
  res.json(reservas);
}));

// GET one booking by numeric id
router.get("/reservas/:id", handle(async (req, res) => {
  const id = parseId(req, res);
  if (id === null) return;
  const reserva = await Reserva.findOne({ id });
  if (!reserva) return res.status(404).json({ mensaje: "Reserva no encontrada" });
  res.json(reserva);
}));

// POST create a booking (the id is assigned automatically: 1, 2, 3...)
router.post("/reservas", handle(async (req, res) => {
  if (!req.body || Object.keys(req.body).length === 0) {
    return res.status(400).json({ mensaje: "La reserva está vacía" });
  }
  const id = await getNextSequence("reservaId");
  const reserva = await Reserva.create({ id, ...pick(req.body, { partial: true }) });
  res.status(201).json(reserva);
}));

// PUT replace a booking
router.put("/reservas/:id", handle(async (req, res) => {
  const id = parseId(req, res);
  if (id === null) return;
  const reserva = await Reserva.findOneAndUpdate({ id }, pick(req.body), { new: true });
  if (!reserva) return res.status(404).json({ mensaje: "Reserva no encontrada" });
  res.json(reserva);
}));

// PATCH update some fields of a booking
router.patch("/reservas/:id", handle(async (req, res) => {
  const id = parseId(req, res);
  if (id === null) return;
  const reserva = await Reserva.findOneAndUpdate({ id }, pick(req.body, { partial: true }), { new: true });
  if (!reserva) return res.status(404).json({ mensaje: "Reserva no encontrada" });
  res.json(reserva);
}));

// DELETE a booking
router.delete("/reservas/:id", handle(async (req, res) => {
  const id = parseId(req, res);
  if (id === null) return;
  const reserva = await Reserva.findOneAndDelete({ id });
  if (!reserva) return res.status(404).json({ mensaje: "Reserva no encontrada" });
  res.json(reserva);
}));

module.exports = router;
