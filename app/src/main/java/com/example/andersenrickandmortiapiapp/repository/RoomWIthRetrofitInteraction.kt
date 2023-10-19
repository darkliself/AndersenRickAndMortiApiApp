package com.example.andersenrickandmortiapiapp.repository

import com.example.andersenrickandmortiapiapp.data.mappers.toCharacterEntity
import com.example.andersenrickandmortiapiapp.data.mappers.toEpisodeEntity
import com.example.andersenrickandmortiapiapp.data.mappers.toLocationEntity
import com.example.andersenrickandmortiapiapp.data.room.RoomDataBase
import com.example.andersenrickandmortiapiapp.data.room.model.ralations.CharacterEpisodeCrossRef
import com.example.andersenrickandmortiapiapp.data.room.model.ralations.LocationCharacterCrossRef
import com.example.andersenrickandmortiapiapp.retrofit.models.characters.CharacterInfo
import com.example.andersenrickandmortiapiapp.retrofit.models.episodes.EpisodesInfo
import com.example.andersenrickandmortiapiapp.retrofit.models.locatons.LocationInfo
import javax.inject.Inject

class RoomWIthRetrofitInteraction  @Inject constructor(
    private val db: RoomDataBase
) {

    fun saveToDataBaseCharacters ( characters: List<CharacterInfo>) {
        db.characterDao.insertListOfCharacters((characters.map { it.toCharacterEntity() }))
        characters.forEach { character ->
            character.episode.forEach { episode ->
                db.characterDao.insertCharacterEpisodeCrossRef(
                    CharacterEpisodeCrossRef(character.id, episode.split("/").last().toInt())
                )
            }
        }
    }

    fun saveToDataBaseEpisodes ( episodes: List<EpisodesInfo>) {
        db.episodeDao.insertListOfEpisode(episodes.map { it.toEpisodeEntity() })
        episodes.forEach { episode ->
            episode.characters.forEach { character ->
                db.characterDao.insertCharacterEpisodeCrossRef(
                    CharacterEpisodeCrossRef(episode.id, character.split("/").last().toInt())
                )
            }
        }
    }

    fun saveToDataBaseLocations (locations: List<LocationInfo>) {
        db.locationDao.insertListOfLocations(locations.map { it.toLocationEntity() })
        locations.forEach { location ->
            location.residents.forEach { character ->
                db.locationDao.insertLocationCharacterCrossRef(
                    LocationCharacterCrossRef(location.id, character.split("/").last().toInt())
                )
            }
        }
    }
}
