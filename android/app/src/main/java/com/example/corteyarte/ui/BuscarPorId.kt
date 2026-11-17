package com.example.corteyarte.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.corteyarte.modules.Reserva
import com.example.corteyarte.modules.ReservaViewModel
import kotlinx.coroutines.launch

@Composable
fun ReservaDetailScreen(
    viewModel: ReservaViewModel
) {
    var id by remember { mutableStateOf("") }
    LaunchedEffect(id) {
        id.toIntOrNull()?.let { viewModel.buscarReservaPorId(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Sección de búsqueda
        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("ID del reserva") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            id.toIntOrNull()?.let { viewModel.buscarReservaPorId(it) }
        }) {
            Text("Buscar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar resultados
        viewModel.reservaDetail?.let { reserva ->
            LazyColumn {
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Blue,
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Campos de la reserva...
                            Text("Id: ${reserva.id}")
                            Text("Nickname: ${reserva.nickname}")
                            Text("Sala: ${reserva.sala}")
                            Text("Carnet: ${reserva.carnet}")
                            Text("Email: ${reserva.email}")
                            Text("Fecha: ${reserva.fecha}")
                            Text("Teléfono: ${reserva.telefono}")
                            Text("Hora Inicio: ${reserva.horaInicio}")
                            Text("Hora Fin: ${reserva.horaFin}")
                            Text("Numero de Personas: ${reserva.numeroPersonas}")
                            Text("Comentario: ${reserva.comentario}")

                        }
                    }
                }
            }
        }
    }
}
