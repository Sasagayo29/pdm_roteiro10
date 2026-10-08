package com.example.pdm_roteiro10

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GroupForm(viewModel: GroupsViewModel, onNavigateBack: () -> Unit) {
    val groupToEdit = viewModel.currentGroup
    var name by remember { mutableStateOf(groupToEdit?.name ?: "") }
    var description by remember { mutableStateOf(groupToEdit?.description ?: "") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (groupToEdit == null) "Novo Grupo" else "Editar Grupo",
                style = MaterialTheme.typography.titleLarge
            )
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nome do Grupo") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descrição") }, modifier = Modifier.fillMaxWidth())

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedButton(onClick = { onNavigateBack() }, enabled = !viewModel.isLoading) {
                    Text("Cancelar")
                }
                Button(
                    enabled = !viewModel.isLoading,
                    onClick = {
                        val group = Group(id = groupToEdit?.id ?: 0, name = name, description = description)
                        viewModel.saveGroup(group) { onNavigateBack() }
                    }
                ) {
                    Text("Salvar")
                }
            }
        }

        if (viewModel.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}