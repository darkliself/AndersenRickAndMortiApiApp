package com.example.andersenrickandmortiapiapp.repository

import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.api.RickAndMortyApiInterface
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.Info
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters.character_list.CharacterInfo
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters.character_list.Characters
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes.episodes_list.Episodes
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes.episodes_list.EpisodesInfo
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.locatons.LocationInfo
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.locatons.Locations
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(
    private val rickAndMortyApiService: RickAndMortyApiInterface,

    ) {

    suspend fun getCharacters(page: Int): Characters {
        return rickAndMortyApiService.getCharactersList(page)
    }

    suspend fun getCharactersByIDs(ids: String): List<CharacterInfo> {
        return rickAndMortyApiService.getCharactersByIDs(ids)
    }

    suspend fun searchCharacters(
        query: CharterSearchQuery
    ): Characters {
        var result: Characters
        try {
            result = rickAndMortyApiService.searchCharacters(
                name = query.name,
                status = query.status,
                species = query.species,
                type = query.type,
                gender = query.gender
            )
        } catch (e: Exception) {
            result = Characters(
                info = Info(
                    count = 0,
                    next = "",
                    pages = 0,
                    prev = ""
                ), listOfCharacters = listOf()
            )
        }
        return result
    }

    suspend fun getCharacterDetails(id: Int): CharacterInfo {
        return rickAndMortyApiService.getCharacterDetails(id)
    }

    suspend fun getEpisodes(page: Int): Episodes {

        return rickAndMortyApiService.getEpisodesList(page)
    }

    suspend fun getEpisodesByID(idList: List<String>): List<EpisodesInfo> {
        return rickAndMortyApiService.getEpisodesByID(idList.joinToString(","))
    }

    suspend fun getEpisodeDetail(id: Int): EpisodesInfo {
        return rickAndMortyApiService.getEpisodesDetails(id)
    }

    suspend fun searchEpisodes(
        name: String?,
        episode: String?
    ): Episodes? {
        var result: Episodes?
        try {
            result = rickAndMortyApiService.searchEpisodes(name, episode)
        } catch (e: Exception) {
            result = Episodes(
                info = Info(
                    count = 0,
                    next = "",
                    pages = 0,
                    prev = ""
                ), listOfEpisodes = listOf()
            )
        }
        return result
    }

    suspend fun getLocations(page: Int): Locations {
        return rickAndMortyApiService.getLocationsList(page)
    }

    suspend fun getLocationDetails(id: Int): LocationInfo {
        return rickAndMortyApiService.getLocationDetails(id)
    }

    suspend fun searchLocations(
        name: String?,
        type: String?,
        dimension: String?,
    ): Locations? {
        var result: Locations?
        try {
            result = rickAndMortyApiService.searchLocations(name, type, dimension)
        } catch (e: Exception) {
            result = Locations(
                info = Info(
                    count = 0,
                    next = "",
                    pages = 0,
                    prev = ""
                ),  locationsList = listOf()
            )
        }
        return result
    }
}

data class CharterSearchQuery(
    val name: String? = null,
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    var gender: String? = null
)

fun CharterSearchQuery.noneToNull(): CharterSearchQuery {
    val noneGender = if (gender == "none") null else gender
    val noneStatus = if (status == "none") null else status
    return CharterSearchQuery(
        name = name,
        status = noneStatus,
        species = species,
        type = type,
        gender = noneGender,
    )
}