package com.example.andersenrickandmortiapiapp.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode_ids_table")
data class EpisodesIdsEntity(
    @PrimaryKey
    val id: Int,
    val air_date: String,
    val characters: String,
    val created: String,
    val episode: String,
    val name: String,
    val url: String
)
