package com.example.pdm_roteiro10

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VehiclesViewModel : ViewModel() {
    private val _vehicles = MutableStateFlow<List<Vehicle>>(
        listOf(Vehicle(1, "Yamaha", "MT-03", "2024"))
    )
    val vehicles: StateFlow<List<Vehicle>> = _vehicles.asStateFlow()

    private var _currentVehicle by mutableStateOf<Vehicle?>(null)
    val currentVehicle: Vehicle? get() = _currentVehicle

    fun setCurrentVehicle(vehicle: Vehicle?) {
        _currentVehicle = vehicle
    }

    fun saveVehicle(vehicle: Vehicle) {
        val currentList = _vehicles.value.toMutableList()
        if (vehicle.id == 0) {
            val newId = (currentList.maxOfOrNull { it.id } ?: 0) + 1
            currentList.add(vehicle.copy(id = newId))
        } else {
            val index = currentList.indexOfFirst { it.id == vehicle.id }
            if (index != -1) {
                currentList[index] = vehicle
            }
        }
        _vehicles.value = currentList
    }
}