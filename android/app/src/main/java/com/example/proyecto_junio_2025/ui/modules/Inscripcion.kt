package com.example.proyecto_junio_2025.ui.modules

import android.provider.ContactsContract.CommonDataKinds.Email

// Este data class representa el objeto que usarás en la app y que corresponde a lo que la API retorna.

data class Inscripcion(
    val nombre: String,
    val apellidos: String,
    val email: String,
    val telefono: Int
)

