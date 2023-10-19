package com.example.andersenrickandmortiapiapp.data.room.model.ralations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.andersenrickandmortiapiapp.data.room.model.entity.CharacterEntity
import com.example.andersenrickandmortiapiapp.data.room.model.entity.EpisodeEntity

data class CharacterWithEpisodes(
    @Embedded val character_id: CharacterEntity,
    @Relation(
        parentColumn = "character_id",
        entityColumn = "episode_id",
        associateBy = Junction(CharacterEpisodeCrossRef::class)
    )
    val episode_id: List<EpisodeEntity>
)