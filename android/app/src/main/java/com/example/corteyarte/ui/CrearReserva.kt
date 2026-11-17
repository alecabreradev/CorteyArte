package com.example.corteyarte.ui


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.corteyarte.modules.Reserva
import com.example.corteyarte.modules.ReservaViewModel
import kotlinx.coroutines.launch
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaCreateScreen(viewModel: ReservaViewModel) {

    val scrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val salas = listOf("Sala Principal", "Sala Taller", "Sala de Reuniones")
    var nickname by remember { mutableStateOf("") }
    var sala by remember { mutableStateOf("") }
    var carnet by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var horaInicio by remember { mutableStateOf("") }
    var horaFin by remember { mutableStateOf("") }
    var numeroPersonas by remember { mutableStateOf("") }
    var comentario by remember { mutableStateOf("") }

    var letrasError by remember { mutableStateOf(false) }
    var numerosError by remember { mutableStateOf(false) }
    var longitudError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var fechaError by remember { mutableStateOf(false) }
    var horaInicioError by remember { mutableStateOf(false) }
    var horaFinError by remember { mutableStateOf(false) }

    // Resetear campos
    fun clearForm() {
        nickname = ""
        sala = ""
        carnet = ""
        email = ""
        fecha = ""
        telefono = ""
        horaInicio = ""
        horaFin = ""
        numeroPersonas = ""
        comentario = ""
    }

    fun validarLetras(cadena: String): Boolean {
        val regex = Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")
        return regex.matches(cadena)
    }

    fun validarNumeros(cadena: String): Boolean {
        val regex = Regex("^[0-9]+$")
        return regex.matches(cadena)
    }

    fun validarLongitud(cadena: String): Boolean {
        return cadena.length in 9..15  // Corrección de validación
    }

    fun validarEmail(email: String): Boolean {
        val regex = Regex("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$") // Validación básica de email
        return regex.matches(email)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = sala,
                onValueChange = {},
                readOnly = true,
                label = { Text("Sala") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
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
        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it
                            letrasError = !validarLetras(it) },
            label = { Text("Nickname") },
            modifier = Modifier.fillMaxWidth(),
            isError = letrasError
        )
        if (letrasError)
        { Text("El nombre solo puede contener letras y espacios.", color = androidx.compose.ui.graphics.Color.Red) }

        OutlinedTextField(
            value = carnet,
            onValueChange = { carnet = it },
            label = { Text("Carnet") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it
                            numerosError = !validarNumeros(it)
                            longitudError = !validarLongitud(it)},
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth(),
            isError = numerosError || longitudError
            )
        if (numerosError)
        { Text("El teléfono solo puede contener números.", color = androidx.compose.ui.graphics.Color.Red) }
        if (longitudError)
        { Text("La longitud del teléfono debe ser entre 9-15.", color = androidx.compose.ui.graphics.Color.Red) }

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = !validarEmail(it)
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError
        )
        if (emailError) {
            Text("Formato de email incorrecto. Usa usuario@dominio.com", color = androidx.compose.ui.graphics.Color.Red)
        }

        OutlinedTextField(
            value = numeroPersonas,
            onValueChange = { numeroPersonas = it
                            numerosError = !validarNumeros(it)},
            label = { Text("Número de personas") },
            modifier = Modifier.fillMaxWidth(),
            isError = numerosError
        )
        if (emailError) {
            Text("El número de personas solo puede contener números.", color = androidx.compose.ui.graphics.Color.Red)
        }

        OutlinedTextField(
            value = comentario,
            onValueChange = { comentario = it },
            label = { Text("Comentario") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                context,
                { _, y, m, d ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(y, m, d)

                    val today = Calendar.getInstance()

                    if (selectedDate.before(today)) {
                        fechaError = true
                    } else {
                        fecha = "$d/${m + 1}/$y"
                        fechaError = false
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }) {
            Text(if (fecha.isEmpty()) "Seleccionar fecha" else "Fecha: $fecha")
        }
        if (fechaError) {
            Text("No puedes seleccionar una fecha anterior a hoy.", color = androidx.compose.ui.graphics.Color.Red)
        }

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

        Button(onClick = {
            val calendar = Calendar.getInstance()
            TimePickerDialog(
                context,
                { _, h, m ->
                    val inicioSplit = horaInicio.split(":")
                    if (inicioSplit.size == 2) {
                        val horaInicioInt = inicioSplit[0].toInt() * 60 + inicioSplit[1].toInt() // Convierte a minutos
                        val horaFinInt = h * 60 + m

                        if (horaFinInt < horaInicioInt) {
                            horaFinError = true
                        } else {
                            horaFin = String.format("%02d:%02d", h, m)
                            horaFinError = false
                        }
                    } else {
                        horaFin = String.format("%02d:%02d", h, m)
                        horaFinError = false
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }) {
            Text(if (horaFin.isEmpty()) "Hora de fin" else "Fin: $horaFin")
        }

// Mostrar mensaje de error si la hora de fin es anterior a la hora de inicio
        if (horaFinError) {
            Text("La hora de fin no puede ser anterior a la hora de inicio.", color = androidx.compose.ui.graphics.Color.Red)
        }

        Button(onClick = {
            viewModel.agregarReserva(
                Reserva(0, sala, nickname, carnet, telefono, email,fecha,horaInicio,horaFin,numeroPersonas.toInt(),comentario)
            )
            Toast.makeText(context, "Reserva Creada", Toast.LENGTH_LONG).show()
            clearForm()
        }) {
            Text("Crear Reserva")
        }
    }
}

