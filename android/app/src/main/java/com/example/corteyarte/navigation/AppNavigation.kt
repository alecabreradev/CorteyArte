package com.example.corteyarte.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.corteyarte.ui.LoginScreen
import com.example.corteyarte.ui.NavigationDrawer
import com.google.firebase.auth.FirebaseAuth
import com.example.corteyarte.ui.RegisterScreen

@Composable
fun CorteYArteApp() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()

    var isAuthenticated by remember {
        mutableStateOf(auth.currentUser != null)
    }
    var showRegister by remember { mutableStateOf(false) }

    // Escucha cambios en el usuario
    LaunchedEffect(Unit) {
        auth.addAuthStateListener {
            isAuthenticated = it.currentUser != null
        }
    }

    when {
        isAuthenticated -> {
            NavigationDrawer(
                navController = navController,
                onNuevaReservaClick = { /* Tu lógica */ }
            )
        }
        showRegister -> {
            RegisterScreen(
                onRegisterSuccess = { isAuthenticated = true },
                onNavigateToLogin = { showRegister = false }
            )
        }
        else -> {
            LoginScreen(
                onLoginSuccess = { isAuthenticated = true },
                onNavigateToRegister = { showRegister = true }
            )
        }
    }
}
