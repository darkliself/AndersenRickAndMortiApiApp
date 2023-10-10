package com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.api

import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.character_data.CharacterData
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters_list.Characters
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes.EpisodesData
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.locatons.LocationsData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApiInterface {
    //    @GET("character/?page=1/")
    @GET("character/")
//    @GET("character")
    suspend fun getCharactersList(
        @Query("page")
        page: Int
    ): Characters

    //    @GET("episode?page=2")
    @GET("episode/")
    suspend fun getEpisodesList(
        @Query("page")
        page: Int
    ): EpisodesData

    @GET("location/")
    suspend fun getLocationsList(
        @Query("page")
        page: Int
    ): LocationsData

    @GET("character/")
    suspend fun searchCharacters(
        @Query("name")
        name: String?,
        @Query("status")
        status: String?,
        @Query("species")
        species: String?,
        @Query("type")
        type: String?,
        @Query("gender")
        gender: String?,
    ): Characters

    @GET("character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int
    ): CharacterData
}
