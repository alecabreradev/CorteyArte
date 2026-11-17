package com.example.corteyarte.modules

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_junio_2025.ui.modules.Inscripcion
import com.example.proyecto_junio_2025.ui.modules.RetrofitClient
import kotlinx.coroutines.launch

class InscripcionViewModel : ViewModel() {

    var message by mutableStateOf("")
    private val api = RetrofitClient.api

    fun agregarInscripcion(inscripcion: Inscripcion) {
        viewModelScope.launch {
            try {
                val created = api.crearInscripcion(inscripcion)
                message = "Creado: ${created.nombre}"
            } catch (e: Exception) {
                Log.e("InscripcionViewModel", "Error al AGREGAR inscripcion", e)
            }
        }
    }
}

