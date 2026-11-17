package com.example.proyecto_junio_2025.ui.modulo

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_junio_2025.R

data class UniData(
    val titulo: String,
    val descripcion: String,
    val imagen: Int
)
@Composable
fun UniItem(uni: UniData, context: Context, universidades: List<UniData>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = uni.titulo,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(id = uni.imagen),
                    contentDescription = "Logo de ${uni.titulo}",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = uni.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal() {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val universidades = listOf(
        UniData(
            titulo = "Universidad de Cádiz",
            descripcion = "Fundada en 1979, la Universidad de Cádiz (UCA) es una institución pública con amplia oferta de titulaciones tecnológicas.",
            imagen = R.drawable.logo_uca,
        ),
        UniData(
            titulo = "Universidad de Cádiz",
            descripcion = "La Universidad de Granada (UGR) es reconocida por su excelencia investigadora y programas de informática",
            imagen = R.drawable.logo_ugr,
        ),
        UniData(
            titulo = "Universidad Complutense",
            descripcion = "Fundada en 1970. Es una de las universidades más prestigiosas de España y tiene una rica historia que se remonta a la Universidad de Alcalá en siglo XV",
            imagen = R.drawable.logo_comp,
        )

    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(Color(0xFFF5F5F5))
    ) {
        Text(
            text = "I JORNADAS ANDROID STUDIO",
            color = Color(0xFFD32F2F),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Lista de universidades
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(universidades) { uni ->
                UniItem(uni = uni, context = context, universidades = universidades)
            }
        }
    }
}