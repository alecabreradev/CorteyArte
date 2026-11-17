package com.example.corteyarte.modules

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReservaViewModel : ViewModel() {

    //Mantiene el estado de la lista de reservas

    var message by mutableStateOf("")
    private val api = RetrofitClient.api

    private val _reservas = MutableStateFlow<List<Reserva>>(emptyList())
    var reservaDetail by mutableStateOf<Reserva?>(null)
        private set

    private var _reservaParaEditar by mutableStateOf<Reserva?>(null)
    val reservaParaEditar: Reserva? get() = _reservaParaEditar


    val reservas: StateFlow<List<Reserva>> = _reservas


    fun obtenerReservas() {
        viewModelScope.launch {
            try {
                _reservas.value = api.getReservas()
            } catch (e: Exception) {
                Log.e("ReservaViewModel", "Error al OBTENER reservas", e)
            }
        }
    }

    fun buscarReservaPorId(id: Int) {
        viewModelScope.launch {
            try {
                reservaDetail = api.getReservaById(id)
            } catch (e: Exception) {
                Log.e("ReservaViewModel", "Error al OBTENER reserva por ID", e)
                reservaDetail = null
            }
        }
    }
//POST
    fun agregarReserva(reserva: Reserva) {
        viewModelScope.launch {
            try {
                val created = api.crearReserva(reserva)
                message = "Creado: ${created.nickname}"
                obtenerReservas()
            } catch (e: Exception) {
                Log.e("ReservaViewModel", "Error al AGREGAR reserva", e)
            }
        }
    }

    fun setReservaParaEditar(reserva: Reserva) {
        _reservaParaEditar = reserva
    }
// PUT
    fun editarReserva(id: Int, reserva: Reserva) {
        viewModelScope.launch {
            try {
                val mod = api.updateReserva(id, reserva)
                message = "Editado completamente: ${mod.id}"
                obtenerReservas()
            } catch (e: Exception) {
                Log.e("ReservaViewModel", "Error al EDITAR (PUT) reserva", e)
            }
        }
    }

// DELETE
    fun eliminarReserva(id: Int) {
        viewModelScope.launch {
            try {
                api.deleteReserva(id)
                message = "Eliminado ID: $id"
                obtenerReservas()
            } catch (e: Exception) {
                Log.e("ReservaViewModel", "Error al ELIMINAR reserva", e)
            }
        }
    }
}

