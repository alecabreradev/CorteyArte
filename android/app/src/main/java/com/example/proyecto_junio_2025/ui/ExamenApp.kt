package com.example.proyecto_junio_2025.ui

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_junio_2025.ui.modulo.NavigationDrawer
@Composable
fun ExamenApp() {
    val navController = rememberNavController()
    NavigationDrawer(
        navController = navController
    )
}
