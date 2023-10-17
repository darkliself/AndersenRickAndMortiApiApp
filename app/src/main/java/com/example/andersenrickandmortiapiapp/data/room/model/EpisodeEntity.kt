package com.example.andersenrickandmortiapiapp.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode_table")
data class EpisodeEntity(
    @PrimaryKey
    val id: Int,
    val air_date: String,
    val characters: String,
    val created: String,
    val episode: String,
    val name: String,
    val url: String
)


//@Entity(tableName = "character_table")
//data class CharacterEntity(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0,
//    val characterName: String
//)