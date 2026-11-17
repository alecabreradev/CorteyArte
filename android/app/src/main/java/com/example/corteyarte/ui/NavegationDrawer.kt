package com.example.corteyarte.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.EventSeat
import com.example.corteyarte.ui.PantallaLocalizacion
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material.icons.filled.ExitToApp



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(navController: NavHostController,
                     onNuevaReservaClick: () -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (currentRoute != "inicio") {
                ModalDrawerSheet {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Menú",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                    Divider()


                    NavigationDrawerItem(
                        label = { Text("Salas") },
                        icon = { Icon(Icons.Default.EventSeat, contentDescription = null) },
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
                        label = { Text("Reservar") },
                        icon = { Icon(Icons.Default.Menu, contentDescription = null) },
                        selected = currentRoute == "reservas",
                        onClick = {
                            navController.navigate("reservas") {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        label = { Text("Admin") },
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        selected = currentRoute == "Admin",
                        onClick = {
                            navController.navigate("Admin") {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        label = { Text("Localización") },
                        icon = { Icon(Icons.Default.Home, contentDescription = null) }, // Puedes cambiar el ícono
                        selected = currentRoute == "localizacion",
                        onClick = {
                            navController.navigate("localizacion") {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        label = { Text("Cerrar sesión") },
                        icon = { Icon(Icons.Default.ExitToApp, contentDescription = null) },
                        selected = false,
                        onClick = {
                            FirebaseAuth.getInstance().signOut() // Cierra sesión
                            navController.navigate("inicio") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true // Limpia el backstack
                                }
                                launchSingleTop = true
                            }
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )


                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "inicio") {
            composable("inicio") {
                PantallaInicio(navController)
            }
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
                    PantallaSalas()
                }
            }
            composable("reservas") {
                MyScaC(
                    topBar = {
                        MyTopAppBar(
                            onSettingsClick = {
                                scope.launch { drawerState.open() }
                            }
                        )
                    }
                ) {
                    PantallaReservas()
                }
            }
            composable("Admin") {
                MyScaC(
                    topBar = {
                        MyTopAppBar(
                            onSettingsClick = {
                                scope.launch { drawerState.open() }
                            }
                        )
                    }
                ) {
                    ReservaTabLayout()
                }
            }
            composable("localizacion") {
                MyScaC(
                    topBar = {
                        MyTopAppBar(
                            onSettingsClick = {
                                scope.launch { drawerState.open() }
                            }
                        )
                    }
                ) {
                    PantallaLocalizacion()
                }
            }

        }
    }
}
