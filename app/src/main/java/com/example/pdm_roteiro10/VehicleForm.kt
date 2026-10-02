package com.example.pdm_roteiro10

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VehicleForm(
    viewModel: VehiclesViewModel,
    onNavigateBack: () -> Unit
) {
    val vehicleToEdit = viewModel.currentVehicle

    var brand by remember { mutableStateOf(vehicleToEdit?.brand ?: "") }
    var model by remember { mutableStateOf(vehicleToEdit?.model ?: "") }
    var year by remember { mutableStateOf(vehicleToEdit?.year ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (vehicleToEdit == null) "Novo Veículo" else "Editar Veículo",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = brand,
            onValueChange = { brand = it },
            label = { Text("Marca") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = model,
            onValueChange = { model = it },
            label = { Text("Modelo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Ano") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = { onNavigateBack() }) {
                Text("Cancelar")
            }

            Button(onClick = {
                val vehicle = Vehicle(
                    id = vehicleToEdit?.id ?: 0,
                    brand = brand,
                    model = model,
                    year = year
                )
                viewModel.saveVehicle(vehicle)
                onNavigateBack()
            }) {
                Text("Salvar")
            }
        }
    }
}