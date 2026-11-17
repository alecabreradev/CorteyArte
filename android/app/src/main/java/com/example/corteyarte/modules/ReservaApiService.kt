package com.example.corteyarte.modules

// Retrofit usa interfaces para definir cómo debe hacer las peticiones HTTP.

import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReservaApiService {
   // Llama a GET /reservas y devuelve una lista de reservas.
    @GET("reservas")
    suspend fun getReservas(): List<Reserva>

    @GET("reservas/{id}")
    suspend fun getReservaById(@Path("id") id: Int): Reserva


    // Envía un objeto JSON al backend y lo crea en la base de datos.
    @POST("reservas")
    suspend fun crearReserva(@Body reserva: Reserva): Reserva

    // Actualiza todos los campos del objeto con PUT
    @PUT("reservas/{id}")
    @JvmSuppressWildcards
    suspend fun updateReserva(@Path("id") id: Int,@Body reserva: Reserva): Reserva

    // Solo actualiza algunos campos del reserva usando PATCH.
    @PATCH("reservas/{id}")
    @JvmSuppressWildcards //resuelve conflictos en Retrofit y en Room
    suspend fun patchReserva(@Path("id") id: Int, @Body reserva: Map<String, Any>): Reserva

    @DELETE("reservas/{id}")
    suspend fun deleteReserva(@Path("id") id: Int) //Response<Unit>
}