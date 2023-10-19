package com.example.andersenrickandmortiapiapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.andersenrickandmortiapiapp.data.room.model.dao.CharacterDao
import com.example.andersenrickandmortiapiapp.data.room.model.dao.EpisodeDao
import com.example.andersenrickandmortiapiapp.data.room.model.dao.LocationDao
import com.example.andersenrickandmortiapiapp.data.room.model.entity.CharacterEntity
import com.example.andersenrickandmortiapiapp.data.room.model.entity.EpisodeEntity
import com.example.andersenrickandmortiapiapp.data.room.model.entity.LocationEntity
import com.example.andersenrickandmortiapiapp.data.room.model.ralations.CharacterEpisodeCrossRef
import com.example.andersenrickandmortiapiapp.data.room.model.ralations.LocationCharacterCrossRef


@Database(
    entities = [
        CharacterEntity::class,
        EpisodeEntity::class,
        LocationEntity::class,
        CharacterEpisodeCrossRef::class,
        LocationCharacterCrossRef::class
    ],
    version = 1
)

abstract class RoomDataBase : RoomDatabase() {
    abstract val characterDao: CharacterDao
    abstract val episodeDao: EpisodeDao
    abstract val locationDao: LocationDao
}
