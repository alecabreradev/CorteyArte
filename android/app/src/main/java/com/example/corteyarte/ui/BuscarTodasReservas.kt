package com.example.corteyarte.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.corteyarte.modules.ReservaViewModel
import com.example.corteyarte.modules.Reserva
import kotlinx.coroutines.launch

@Composable
fun ListaReservas(viewModel: ReservaViewModel = ReservaViewModel()) {
    var reservaEditando by remember { mutableStateOf<Reserva?>(null) }
    val reservas by viewModel.reservas.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.obtenerReservas()
    }

    if (reservaEditando != null) {
        // Mostrar solo el formulario de edición
        ReservaUpdateScreen(
            viewModel = viewModel,
            onCancel = { reservaEditando = null },
            onUpdate = {
                scope.launch {
                    snackbarHostState.showSnackbar("Reserva actualizada")
                }
                reservaEditando = null
            }
        )
    } else {
        if (reservas.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay reservas disponibles")
            }
        } else {
            LazyColumn {
                items(reservas) { reserva ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Red,
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                    Column(modifier = Modifier.padding(16.dp)) {
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
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(onClick = {
                                        viewModel.setReservaParaEditar(reserva)
                                        reservaEditando = reserva

                                    }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                                    }
                                    IconButton(onClick = {

                                        scope.launch {
                                            viewModel.eliminarReserva(reserva.id)
                                            Toast.makeText(context, "Reserva Eliminada", Toast.LENGTH_LONG).show()
                                        }
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                    }
                                }
                            }
                        }    
                    }
                }
            }
        }
    Spacer(modifier = Modifier.height(16.dp))
}

