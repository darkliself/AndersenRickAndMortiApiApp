package com.example.andersenrickandmortiapiapp.data.room.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode_table")
data class EpisodeEntity(
    @PrimaryKey
    @ColumnInfo(name = "episode_id")
    val episodeId: Int,
    @ColumnInfo(name = "air_data")
    val airDate: String,
    val created: String,
    val episode: String,
    val name: String,
    val url: String
)
