const mongoose = require("mongoose");

const ReservaSchema = new mongoose.Schema({
    id: Number,
    sala: String,
    nickname: String,
    carnet: String,
    telefono: String,
    email: String,
    fecha: String,
    horaInicio: String,
    horaFin: String,
    numeroPersonas: Number,
    comentario: String,
});


module.exports = mongoose.model("Reserva", ReservaSchema);