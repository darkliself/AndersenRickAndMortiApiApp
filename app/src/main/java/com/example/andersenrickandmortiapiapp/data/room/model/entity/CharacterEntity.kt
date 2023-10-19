package com.example.andersenrickandmortiapiapp.data.room.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class CharacterEntity (
    @PrimaryKey
    @ColumnInfo(name = "character_id")
    val characterId: Int,
    val created: String,
    val gender: String,
    val image: String,
    @ColumnInfo(name = "location_name")
    val locationName: String,
    val location: String,
    val name: String,
    val origin: String,
    @ColumnInfo(name = "origin_name")
    val originName: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)
