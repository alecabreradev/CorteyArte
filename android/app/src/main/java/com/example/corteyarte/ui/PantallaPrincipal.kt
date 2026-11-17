package com.example.corteyarte.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaPrincipal() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF)), // Fondo gris claro
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Bienvenido a la pantalla principal",
            fontSize = 22.sp,
            color = Color.Black
        )
    }
}