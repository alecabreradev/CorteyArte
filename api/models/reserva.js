const mongoose = require("mongoose");

const ReservaSchema = new mongoose.Schema(
  {
    // Defaults keep every field present, so the Android app (non-null Kotlin fields) never receives null
    id: { type: Number, unique: true, index: true }, // public numeric id used by the app and the website
    sala: { type: String, default: "" },
    nickname: { type: String, default: "" },
    carnet: { type: String, default: "" },
    telefono: { type: String, default: "" },
    email: { type: String, default: "" },
    fecha: { type: String, default: "" },
    horaInicio: { type: String, default: "" },
    horaFin: { type: String, default: "" },
    numeroPersonas: { type: Number, default: 1 },
    comentario: { type: String, default: "" },
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
