package com.example.pdm_roteiro10

sealed class Screen(val route: String, val title: String) {
    object VehiclesList : Screen("vehicles_list", "Veículos")
    object VehicleForm : Screen("vehicle_form", "Formulário de Veículo")
    object GroupsList : Screen("groups_list", "Grupos")
    object GroupForm : Screen("group_form", "Formulário de Grupo")
}