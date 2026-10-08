package com.example.pdm_roteiro10

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VehiclesViewModel(private val dao: VehicleDao) : ViewModel() {

    val vehicles: StateFlow<List<Vehicle>> = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private var _currentVehicle by mutableStateOf<Vehicle?>(null)
    val currentVehicle: Vehicle? get() = _currentVehicle

    var isLoading by mutableStateOf(false)
        private set

    fun setCurrentVehicle(vehicle: Vehicle?) {
        _currentVehicle = vehicle
    }

    fun saveVehicle(vehicle: Vehicle, onSaved: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            delay(500)
            dao.insert(vehicle)
            isLoading = false
            onSaved()
        }
    }
}