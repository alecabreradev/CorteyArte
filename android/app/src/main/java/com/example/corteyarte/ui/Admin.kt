package com.example.corteyarte.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.corteyarte.modules.ReservaViewModel

@Composable
fun ReservaTabLayout(viewModel: ReservaViewModel = ReservaViewModel()) {
    val tabs = listOf("Listar", "Buscar ID")
    var selectedTab by remember { mutableStateOf(0) }

    Column {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        when (selectedTab) {
            0 -> ListaReservas(viewModel)   //GET All
            1 -> ReservaDetailScreen(viewModel) // GET Id
        }
        Text(viewModel.message, modifier = Modifier.padding(8.dp), color = Color.Green)
    }
}