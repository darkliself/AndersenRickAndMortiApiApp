package com.example.andersenrickandmortiapiapp.repository

import com.example.andersenrickandmortiapiapp.data.mappers.toCharacterEntity
import com.example.andersenrickandmortiapiapp.data.mappers.toEpisodeEntity
import com.example.andersenrickandmortiapiapp.data.mappers.toLocationEntity
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters.character_list.CharacterInfo
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters.character_list.Characters
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes.episodes_list.Episodes
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes.episodes_list.EpisodesInfo
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.locatons.LocationInfo
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.locatons.Locations
import javax.inject.Inject

class UseCase @Inject constructor(
    private val rickAndMortyApiService: RickAndMortyRepository,
    private val roomDbRepository: RoomDbRepository
) {
    var episodePagesCount = 0

    suspend fun getEpisodesAndSaveToDb(page: Int): List<EpisodesInfo> {

        val result = rickAndMortyApiService.getEpisodes(page)
        roomDbRepository.addEpisodes(result.listOfEpisodes.map { it.toEpisodeEntity() })
        episodePagesCount = result.info.pages
        return result.listOfEpisodes
    }

//    suspend fun getEpisodeDetails(id: Int): EpisodesInfo {
//        return rickAndMortyApiService.getEpisodeDetail(id)
//    }

    suspend fun searchEpisodes(name: String, episode: String): Episodes {

        val result = rickAndMortyApiService.searchEpisodes(name, episode)
        if (result?.listOfEpisodes!!.isNotEmpty()) {
            roomDbRepository.addEpisodes(result?.listOfEpisodes!!.map { it.toEpisodeEntity() })
        }
        return result
    }

    var locationPagesCount = 0

    suspend fun getLocationsAndSaveToDb(page: Int): List<LocationInfo> {
        val result = rickAndMortyApiService.getLocations(page)
        roomDbRepository.addLocations(result.locationsList.map {
            it.toLocationEntity()
        })
        locationPagesCount = result.info.pages
        return result.locationsList
    }

    suspend fun searchLocation(name: String, type: String, dimension: String): Locations {
        val result = rickAndMortyApiService.searchLocations(name, type, dimension)
        if (result?.locationsList!!.isNotEmpty()) {
            roomDbRepository.addLocations(result?.locationsList!!.map { it.toLocationEntity() })
        }
        return result
    }

//    suspend fun getLocationDetails(id: Int): LocationDetails {
//        return rickAndMortyApiService.getLocationDetails(id)
//    }

    var characterPagesCount = 0

    suspend fun getCharactersAndSaveToDb(page: Int): List<CharacterInfo> {
        val result = rickAndMortyApiService.getCharacters(page)
        roomDbRepository.addCharacters(result.listOfCharacters.map { it.toCharacterEntity() })
        characterPagesCount = result.info.pages
        return result.listOfCharacters
    }

    suspend fun getCharactersByIds(ids: List<String>): List<CharacterInfo> {
        val result = rickAndMortyApiService.getCharactersByIDs(ids.joinToString(","))
        return result
    }

//    suspend fun getCharacterDetails(id: Int): CharacterInfo {
//        return rickAndMortyApiService.getCharacterDetails(id)
//    }

    suspend fun searchCharacters(query: CharterSearchQuery): Characters {

        val result = rickAndMortyApiService.searchCharacters(query)
        if (result != null && result.listOfCharacters.isNotEmpty()) {
            roomDbRepository.addCharacters(result.listOfCharacters.map { it.toCharacterEntity() })
        }
        return result
    }
}


