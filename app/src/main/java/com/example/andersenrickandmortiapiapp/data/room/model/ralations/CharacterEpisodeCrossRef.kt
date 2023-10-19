package com.example.andersenrickandmortiapiapp.data.room.model.ralations

import androidx.room.Entity

@Entity(primaryKeys = ["character_id", "episode_id"])
data class CharacterEpisodeCrossRef(
    val character_id: Int,
    val episode_id: Int
)
