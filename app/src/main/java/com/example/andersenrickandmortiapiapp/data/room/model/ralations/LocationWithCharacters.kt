package com.example.andersenrickandmortiapiapp.data.room.model.ralations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.andersenrickandmortiapiapp.data.room.model.entity.CharacterEntity
import com.example.andersenrickandmortiapiapp.data.room.model.entity.LocationEntity


data class LocationWithCharacters(
    @Embedded val location_id: LocationEntity,
    @Relation(
        parentColumn = "location_id",
        entityColumn = "character_id",
        associateBy = Junction(LocationCharacterCrossRef::class)
    )
    val character_id: List<CharacterEntity>
)