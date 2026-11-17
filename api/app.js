const express = require("express");
const cors = require("cors");
const reservasRouter = require("./routes/reservas");

const app = express();

app.use(cors()); // lets the React website (another port/domain) call the API
app.use(express.json());

// All routes live under /api, e.g. GET http://localhost:3000/api/reservas
app.use("/api", reservasRouter);

app.get("/api/health", (req, res) => res.json({ status: "ok" }));

module.exports = app;
