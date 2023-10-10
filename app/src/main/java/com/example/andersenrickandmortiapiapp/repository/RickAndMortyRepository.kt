package com.example.andersenrickandmortiapiapp.repository

import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.api.RickAndMortyApiInterface
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.character_data.CharacterData
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters_list.Characters
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes.EpisodesData
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.locatons.LocationsData
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(
    private val rickAndMortyApiService: RickAndMortyApiInterface
) {
    suspend fun getCharacters(page: Int): Characters {
        return rickAndMortyApiService.getCharactersList(page)
    }

    suspend fun getEpisodes(page: Int): EpisodesData {
        return rickAndMortyApiService.getEpisodesList(page)
    }

    suspend fun getLocations(page: Int): LocationsData {
        return rickAndMortyApiService.getLocationsList(page)
    }

    suspend fun searchCharacters(): Characters {
        return rickAndMortyApiService.searchCharacters(
            name = "Rick",
            status = null,
            species = null,
            type = null,
            gender = null
        )
    }
    suspend fun getSingleCharacter(id: Int): CharacterData {
        return rickAndMortyApiService.getSingleCharacter(id)
    }
}