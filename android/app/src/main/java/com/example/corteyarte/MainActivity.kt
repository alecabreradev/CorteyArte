package com.example.corteyarte

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.corteyarte.navigation.CorteYArteApp
import com.example.corteyarte.ui.theme.CorteYArteTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this) // <--- Agrega esto
        setContent {
            CorteYArteTheme {
                CorteYArteApp()
            }
        }
    }

}

