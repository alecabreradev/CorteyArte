const express = require("express");
const router = express.Router();
const Reserva = require("../models/reserva");
const getNextSequence = require("../helpers/getNextSequence");

// GET todos
router.get("/reservas", async (req, res) => {
  try {
    const reservas = await Reserva.find();  // Sin filtros, devuelve todos
    res.json(reservas);
  } catch (err) {
    res.status(500).json({ error: "Error al obtener las reservas" });
  }
});

// GET por ID
router.get('/reservas/:id', (req, res) => {
  const reserva = reservas.find(a => a.id === parseInt(req.params.id));
  reserva ? res.json(reserva) : res.status(404).json({ mensaje: 'reserva no encontrada' });
});

// POST crear
router.post('/reservas', (req, res) => {
  const { sala, nickname, carnet, telefono, email, fecha, horaInicio, horaFin, numeroPersonas, comentario } = req.body;
  
  const nuevaReserva = {
    id: reservas.length + 1,
    sala,
    nickname,
    carnet,
    telefono,
    email,
    fecha,
    horaInicio,
    horaFin,
    numeroPersonas,
    comentario
  };
  
  reservas.push(nuevaReserva);
  res.status(201).json(nuevaReserva);
});


// PUT reemplazo completo
router.put('/reservas/:id', (req, res) => {
  const index = reservas.findIndex(r => r.id === parseInt(req.params.id));
  if (index === -1) return res.status(404).json({ mensaje: 'Reserva no encontrada' });

  const { sala, nickname, carnet, telefono, email, fecha, horaInicio, horaFin, numeroPersonas, comentario } = req.body;
  
  reservas[index] = {
    id: parseInt(req.params.id),
    sala,
    nickname,
    carnet,
    telefono,
    email,
    fecha,
    horaInicio,
    horaFin,
    numeroPersonas,
    comentario
  };
  
  res.json(reservas[index]);
});



// PATCH actualización parcial
router.patch('/reservas/:id', (req, res) => {
  const reserva = reservas.find(r => r.id === parseInt(req.params.id));
  if (!reserva) return res.status(404).json({ mensaje: 'Reserva no encontrada' });

  const { sala, nickname, carnet, telefono, email, fecha, horaInicio, horaFin, numeroPersonas, comentario } = req.body;
  
  if (sala !== undefined) reserva.sala = sala;
  if (nickname !== undefined) reserva.nickname = nickname;
  if (carnet !== undefined) reserva.carnet = carnet;
  if (telefono !== undefined) reserva.telefono = telefono;
  if (email !== undefined) reserva.email = email;
  if (fecha !== undefined) reserva.fecha = fecha;
  if (horaInicio !== undefined) reserva.horaInicio = horaInicio;
  if (horaFin !== undefined) reserva.horaFin = horaFin;
  if (numeroPersonas !== undefined) reserva.numeroPersonas = numeroPersonas;
  if (comentario !== undefined) reserva.comentario = comentario;

  res.json(reserva);
});

// DELETE
router.delete('/reservas/:id', (req, res) => {
  const index = reservas.findIndex(r => r.id === parseInt(req.params.id));
  if (index === -1) return res.status(404).json({ mensaje: 'Reserva no encontrada' });

  const eliminado = reservas.splice(index, 1);
  res.json(eliminado[0]);
});






module.exports = router;