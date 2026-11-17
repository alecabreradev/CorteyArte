package com.example.corteyarte.modules

import java.util.UUID

// Este data class representa el objeto que usarás en la app y que corresponde a lo que la API retorna.

data class Reserva(
    val id: Int,
    val sala: String,
    val nickname: String,
    val carnet: String,
    val telefono: String,
    val email: String,
    val fecha: String,
    val horaInicio: String,
    val horaFin: String,
    val numeroPersonas: Int,
    val comentario: String
)

// Equivale a cada entrada de la API animales que tenías en Node.js.
