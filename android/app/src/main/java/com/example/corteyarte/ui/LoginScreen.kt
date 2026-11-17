package com.example.corteyarte.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showResetPasswordDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar sesión", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = null
            },
            label = { Text("Correo electrónico") },
            singleLine = true,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = null
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage != null) {
            Text(text = errorMessage!!, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Email y contraseña son obligatorios"
                } else {
                    isLoading = true
                    auth.signInWithEmailAndPassword(email.trim(), password.trim())
                        .addOnCompleteListener { task ->
                            isLoading = false
                            if (task.isSuccessful) {
                                onLoginSuccess()
                            } else {
                                errorMessage = task.exception?.message ?: "Error desconocido"
                            }
                        }
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes cuenta? Regístrate")
        }

        TextButton(onClick = {
            showResetPasswordDialog = true
        }) {
            Text("¿Olvidaste tu contraseña?")
        }

        if (showResetPasswordDialog) {
            var resetEmail by remember { mutableStateOf("") }
            var resetError by remember { mutableStateOf<String?>(null) }
            var resetSuccess by remember { mutableStateOf(false) }

            AlertDialog(
                onDismissRequest = {
                    showResetPasswordDialog = false
                    resetEmail = ""
                    resetError = null
                    resetSuccess = false
                },
                title = { Text("Restablecer contraseña") },
                text = {
                    Column {
                        if (resetSuccess) {
                            Text("Se ha enviado un correo para restablecer la contraseña.")
                        } else {
                            OutlinedTextField(
                                value = resetEmail,
                                onValueChange = {
                                    resetEmail = it
                                    resetError = null
                                },
                                label = { Text("Correo electrónico") },
                                singleLine = true
                            )
                            if (resetError != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(resetError!!, color = Color.Red)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (resetEmail.isBlank()) {
                            resetError = "El correo es obligatorio"
                        } else {
                            FirebaseAuth.getInstance()
                                .sendPasswordResetEmail(resetEmail.trim())
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        resetSuccess = true
                                    } else {
                                        resetError = task.exception?.localizedMessage ?: "Error al enviar el correo"
                                    }
                                }
                        }
                    }) {
                        Text(if (resetSuccess) "Cerrar" else "Enviar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showResetPasswordDialog = false
                        resetEmail = ""
                        resetError = null
                        resetSuccess = false
                    }) {
                        Text("Cerrar")
                    }
                }
            )
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}
