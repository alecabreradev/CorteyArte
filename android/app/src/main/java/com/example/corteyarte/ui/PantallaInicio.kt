package com.example.corteyarte.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.corteyarte.R

@Composable
fun PantallaInicio(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // ✅ Imagen del logo
        Image(
            painter = painterResource(id = R.drawable.logo_app),
            contentDescription = "Logo de Corte y Arte",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 16.dp)
        )

        // ✅ Título y nombres
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Corte & Arte",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Francisco Cabrera", style = MaterialTheme.typography.bodyLarge)
            Text("Julián Cabrera", style = MaterialTheme.typography.bodyLarge)
            Text("Alejandro Cabrera", style = MaterialTheme.typography.bodyLarge)
        }

        // ✅ Botón de navegación
        Button(
            onClick = { navController.navigate("principal") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Entrar", fontSize = 18.sp)
        }
    }
}