package com.example.andersenrickandmortiapiapp.data.mappers

import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes.episodes_list.EpisodesInfo
import com.example.andersenrickandmortiapiapp.data.room.model.EpisodeEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun EpisodesInfo.toEpisodeEntity(): EpisodeEntity {
    val gson = Gson()
    return EpisodeEntity(

        id = id,
        name = name,
        air_date = airDate,
        characters = gson.toJson(characters),
        created = created,
        episode = episode,
        url = url
    )
}

fun EpisodeEntity.toEpisodesInfo(): EpisodesInfo {
    val gson = Gson()
    val listType = object : TypeToken<List<String>>() {}.type
    return EpisodesInfo(
        id = id,
        name = name,
        airDate = air_date,
        characters = gson.fromJson(characters, listType),
        created = created,
        episode = episode,
        url = url
    )
}
