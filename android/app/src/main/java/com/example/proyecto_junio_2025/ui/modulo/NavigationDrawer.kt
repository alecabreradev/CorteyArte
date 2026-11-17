package com.example.proyecto_junio_2025.ui.modulo

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.ExitToApp



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
    drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                Text(
                    "Menú",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Univ. Organizadoras") },
                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                    selected = currentRoute == "principal",
                    onClick = {
                        navController.navigate("principal") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("Inscripciones") },
                    icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    selected = currentRoute == "Inscripciones",
                    onClick = {
                        navController.navigate("Inscripciones") {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "principal") {
            composable("principal") {
                MyScaC(
                    topBar = {
                        MyTopAppBar(
                            onSettingsClick = {
                                scope.launch { drawerState.open() }
                            }
                        )
                    }
                ) {
                    PantallaPrincipal()
                }
            }
            composable("Inscripciones") {
                MyScaC(
                    topBar = {
                        MyTopAppBar(
                            onSettingsClick = {
                                scope.launch { drawerState.open() }
                            }
                        )
                    }
                ) {
                    PantallaInscripciones()
                }
            }
        }
    }
}
