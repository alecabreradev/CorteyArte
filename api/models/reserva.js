const mongoose = require("mongoose");

const ReservaSchema = new mongoose.Schema(
  {
    id: { type: Number, unique: true, index: true }, // public numeric id used by the app and the website
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
  },
  {
    // Hide MongoDB internals (_id, __v) from API responses
    toJSON: {
      transform: (doc, ret) => {
        delete ret._id;
        delete ret.__v;
        return ret;
      },
    },
  }
);

module.exports = mongoose.model("Reserva", ReservaSchema);
