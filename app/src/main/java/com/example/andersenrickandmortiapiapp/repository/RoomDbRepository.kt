package com.example.andersenrickandmortiapiapp.repository

import com.example.andersenrickandmortiapiapp.data.room.ApplicationRoomDB
import com.example.andersenrickandmortiapiapp.data.room.model.CharacterEntity
import com.example.andersenrickandmortiapiapp.data.room.model.EpisodeEntity
import com.example.andersenrickandmortiapiapp.data.room.model.LocationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomDbRepository @Inject constructor(
    val db: ApplicationRoomDB
) {

    fun getAllCharacters(): Flow<List<CharacterEntity>> {
        return db.characterDao.getAllCharacters()
    }

    fun addCharacters(characters: List<CharacterEntity>) {
        db.characterDao.insertListOfCharacters(characters)
    }

    fun getCharacterById(id: Int) {
        db.characterDao.getCharacterById(id)
    }

    fun getAllEpisodes(): Flow<List<EpisodeEntity>> {
        return db.episodeDao.getAllEpisodes()
    }

    fun addEpisodes(episode: List<EpisodeEntity>) {
        db.episodeDao.insertListOfEpisode(episode)
    }

    fun getAllLocation(): Flow<List<LocationEntity>> {
        return db.locationDao.getAllLocations()
    }

    fun getLocationDetails(id: Int): LocationEntity {
       return db.locationDao.getLocationById(id)
    }

    fun addLocations(location: List<LocationEntity>) {
        db.locationDao.insertListOfLocations(location)
    }
}