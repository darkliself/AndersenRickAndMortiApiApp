package com.example.andersenrickandmortiapiapp.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
data class LocationEntity(
    @PrimaryKey
    val id: Int,
    val created: String,
    val dimension: String,
    val name: String,
    val residents: String,
    val type: String,
    val url: String
)
