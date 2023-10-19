package com.example.andersenrickandmortiapiapp.data.room.model.ralations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.andersenrickandmortiapiapp.data.room.model.entity.CharacterEntity
import com.example.andersenrickandmortiapiapp.data.room.model.entity.EpisodeEntity


data class EpisodeWithCharacter(
    @Embedded val episode_id: EpisodeEntity,
    @Relation(
        parentColumn = "episode_id",
        entityColumn = "character_id",
        associateBy = Junction(CharacterEpisodeCrossRef::class)
    )
    val character_id: List<CharacterEntity>
)