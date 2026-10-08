package com.example.pdm_roteiro10

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val brand: String = "",
    val model: String = "",
    val year: String = ""
)