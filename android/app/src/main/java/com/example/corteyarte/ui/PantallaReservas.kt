package com.example.corteyarte.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.util.*
import com.example.corteyarte.modules.Reserva
import com.example.corteyarte.modules.ReservaViewModel
import java.util.Calendar
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.widget.RemoteViews
import androidx.navigation.compose.rememberNavController
import com.example.corteyarte.R

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
fun PantallaReservas(viewModel: ReservaViewModel = ReservaViewModel()) {
    var vistaActual by remember { mutableStateOf("lista") } // "lista", "formulario"
    var listaReservas by remember { mutableStateOf(listOf<Reserva>()) }
    var reservaSeleccionada by remember { mutableStateOf<Reserva?>(null) }
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Crear una Reserva") })
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->

        Column(modifier = Modifier.padding(padding).padding(16.dp)) {

                    ReservaCreateScreen(viewModel)

        }
    }
}
