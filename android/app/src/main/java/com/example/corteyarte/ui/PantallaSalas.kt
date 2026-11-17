package com.example.corteyarte.ui

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.corteyarte.R
import androidx.compose.ui.graphics.Color

import java.io.File
import java.io.FileOutputStream

data class SalaData(
    val titulo: String,
    val descripcion1: String,
    val descripcion2: String,
    val imagenResId: Int,
    val servicios: List<Pair<String, String>>
)

@Composable
fun PantallaSalas() {
    val context = LocalContext.current

    val salas = listOf(
        SalaData(
            titulo = "Sala de Corte",
            descripcion1 = "Espacio moderno para cortes de cabello, con estaciones individuales.",
            descripcion2 = "Cuenta con espejos amplios, sillas hidráulicas y herramientas profesionales.",
            imagenResId = R.drawable.sala_corte,
            servicios = listOf(
                "Sillas hidráulicas" to "Ajustables y cómodas",
                "Espejos LED" to "Iluminación ideal",
                "Lavacabezas" to "Con sistema reclinable",
                "Herramientas" to "Tijeras, máquinas, peines"
            )
        ),
        SalaData(
            titulo = "Sala de Estética",
            descripcion1 = "Zona pensada para tratamientos de belleza y cuidado facial.",
            descripcion2 = "Ambiente relajante con camillas, iluminación tenue y equipo especializado.",
            imagenResId = R.drawable.sala_estetica,
            servicios = listOf(
                "Camillas" to "Ergonómicas y reclinables",
                "Luz cálida" to "Ambiente relajante",
                "Productos estéticos" to "Gamas hipoalergénicas",
                "Equipo especializado" to "Vaporizador, lupa, alta frecuencia"
            )
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(salas) { sala ->
            SalaItem(sala = sala, context = context, salas = salas)
        }
    }
}

@Composable
fun SalaItem(sala: SalaData, context: Context, salas: List<SalaData>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            // Encabezado
            Text(
                text = sala.titulo,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Imagen
            Image(
                painter = painterResource(id = sala.imagenResId),
                contentDescription = "Imagen de ${sala.titulo}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción
            Text(
                text = sala.descripcion1,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = sala.descripcion2,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Título servicios
            Text(
                text = "Servicios e instalaciones",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Lista de servicios
            sala.servicios.forEach { (servicio, descripcion) ->
                ServicioItem(servicio, descripcion)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón PDF
            Button(
                onClick = { generarPdf(context, salas) },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Descargar en PDF")
            }
        }
    }
}
@Composable
fun ServicioItem(titulo: String, descripcion: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = "• $titulo",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = descripcion,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

