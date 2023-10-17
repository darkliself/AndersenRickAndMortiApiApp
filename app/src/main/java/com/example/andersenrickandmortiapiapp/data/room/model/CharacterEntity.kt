package com.example.andersenrickandmortiapiapp.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "character_table")
data class CharacterEntity (
    @PrimaryKey
    val id: Int,
    val created: String,
    val episode: String,
    val gender: String,
    val image: String,
    val location_name: String,
    val location: String,
    val name: String,
    val origin: String,
    val origin_name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)