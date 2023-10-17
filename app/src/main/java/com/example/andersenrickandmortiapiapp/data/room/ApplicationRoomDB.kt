package com.example.andersenrickandmortiapiapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.andersenrickandmortiapiapp.data.room.model.CharacterEntity
import com.example.andersenrickandmortiapiapp.data.room.model.EpisodeEntity
import com.example.andersenrickandmortiapiapp.data.room.model.LocationEntity


@Database(
    entities = [EpisodeEntity::class, LocationEntity::class, CharacterEntity::class],
    version = 1
)

abstract class ApplicationRoomDB : RoomDatabase() {
    abstract val episodeDao: EpisodeDao
    abstract val locationDao: LocationDao
    abstract val characterDao: CharacterDao
}
