package com.example.proyecto_junio_2025.ui.modules

// Retrofit usa interfaces para definir cómo debe hacer las peticiones HTTP.

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface InscripcionApiService {

    @POST("inscritos")
    suspend fun crearInscripcion(@Body inscripcion: Inscripcion): Inscripcion

}