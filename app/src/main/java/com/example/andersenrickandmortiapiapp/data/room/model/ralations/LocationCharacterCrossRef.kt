package com.example.andersenrickandmortiapiapp.data.room.model.ralations

import androidx.room.Entity

@Entity(primaryKeys = ["location_id", "character_id"])
data class LocationCharacterCrossRef(
    val location_id: Int,
    val character_id: Int,
)
