package com.example.corteyarte.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.corteyarte.modules.Reserva
import com.example.corteyarte.modules.ReservaViewModel
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaUpdateScreen(
    viewModel: ReservaViewModel,
    onCancel: () -> Unit,
    onUpdate: () -> Unit
) {
    val scrollState = rememberLazyListState()
    var expanded by remember { mutableStateOf(false) }
    val reserva = viewModel.reservaParaEditar
    val context = LocalContext.current
    val salas = listOf("Sala Principal", "Sala Taller", "Sala de Reuniones")
    var id by remember(reserva?.id) { mutableStateOf(reserva?.id?.toString() ?: "") }
    var nickname by remember(reserva?.nickname) { mutableStateOf(reserva?.nickname ?: "") }
    var sala by remember(reserva?.sala) { mutableStateOf(reserva?.sala ?: "") }
    var carnet by remember(reserva?.carnet) { mutableStateOf(reserva?.carnet ?: "") }
    var email by remember(reserva?.email) { mutableStateOf(reserva?.email ?: "") }
    var fecha by remember(reserva?.fecha) { mutableStateOf(reserva?.fecha ?: "") }
    var telefono by remember(reserva?.telefono) { mutableStateOf(reserva?.telefono ?: "") }
    var horaInicio by remember(reserva?.horaInicio) { mutableStateOf(reserva?.horaInicio ?: "") }
    var horaFin by remember(reserva?.horaFin) { mutableStateOf(reserva?.horaFin ?: "") }
    var numeroPersonas by remember(reserva?.numeroPersonas) { mutableStateOf(reserva?.numeroPersonas?.toString() ?: "") }
    var comentario by remember(reserva?.comentario) { mutableStateOf(reserva?.comentario ?: "") }

    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = sala,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sala") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    salas.forEach { op ->
                        DropdownMenuItem(
                            text = { Text(op) },
                            onClick = {
                                sala = op
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        item {
            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text("Nickname") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = carnet,
                onValueChange = { carnet = it },
                label = { Text("Carnet") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = numeroPersonas,
                onValueChange = { numeroPersonas = it },
                label = { Text("Número de personas") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = comentario,
                onValueChange = { comentario = it },
                label = { Text("Comentario") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Button(onClick = {
                val calendar = Calendar.getInstance()
                DatePickerDialog(
                    context,
                    { _, y, m, d -> fecha = "$d/${m + 1}/$y" },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }) {
                Text(if (fecha.isEmpty()) "Seleccionar fecha" else "Fecha: $fecha")
            }
        }

        item {
            Button(onClick = {
                val calendar = Calendar.getInstance()
                TimePickerDialog(
                    context,
                    { _, h, m -> horaInicio = String.format("%02d:%02d", h, m) },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            }) {
                Text(if (horaInicio.isEmpty()) "Hora de inicio" else "Inicio: $horaInicio")
            }
        }

        item {
            Button(onClick = {
                val calendar = Calendar.getInstance()
                TimePickerDialog(
                    context,
                    { _, h, m -> horaFin = String.format("%02d:%02d", h, m) },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            }) {
                Text(if (horaFin.isEmpty()) "Hora de fin" else "Fin: $horaFin")
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onCancel) {
                    Text("Cancelar")
                }
                Button(onClick = {
                    val reserva = Reserva(
                        id.toInt(),
                        sala,
                        nickname,
                        carnet,
                        telefono,
                        email,
                        fecha,
                        horaInicio,
                        horaFin,
                        numeroPersonas.toInt(),
                        comentario
                    )
                    viewModel.editarReserva(id.toInt(), reserva)
                    onUpdate()
                }) {
                    Text("Actualizar Reserva")
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}