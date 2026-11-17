package com.example.proyecto_junio_2025.ui.modulo

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import java.util.*
import com.example.corteyarte.modules.InscripcionViewModel
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_junio_2025.ui.modules.Inscripcion

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInscripciones(viewModel: InscripcionViewModel = InscripcionViewModel()) {
    val context = LocalContext.current
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "¡Inscribete!",
            fontSize = MaterialTheme.typography.displayLarge.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Carnet Socio") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = { Text("Carnet Socio") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("telefono") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            viewModel.agregarInscripcion(
                Inscripcion(nombre,apellidos,email,telefono.toInt())
            )
            Toast.makeText(context, "Reserva Creada", Toast.LENGTH_LONG).show()
        }) {
            Text("Inscribir")
        }
    }
}
