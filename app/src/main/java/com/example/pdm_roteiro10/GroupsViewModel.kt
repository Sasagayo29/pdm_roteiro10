package com.example.pdm_roteiro10

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GroupsViewModel : ViewModel() {
    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups: StateFlow<List<Group>> = _groups.asStateFlow()

    private var _currentGroup by mutableStateOf<Group?>(null)
    val currentGroup: Group? get() = _currentGroup

    fun setCurrentGroup(group: Group?) {
        _currentGroup = group
    }

    fun saveGroup(group: Group) {
        val currentList = _groups.value.toMutableList()
        if (group.id == 0) {
            val newId = (currentList.maxOfOrNull { it.id } ?: 0) + 1
            currentList.add(group.copy(id = newId))
        } else {
            val index = currentList.indexOfFirst { it.id == group.id }
            if (index != -1) {
                currentList[index] = group
            }
        }
        _groups.value = currentList
    }
}