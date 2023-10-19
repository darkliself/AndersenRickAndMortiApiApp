package com.example.andersenrickandmortiapiapp.data.mappers

import com.example.andersenrickandmortiapiapp.data.room.model.entity.EpisodeEntity
import com.example.andersenrickandmortiapiapp.retrofit.models.episodes.EpisodesInfo


fun EpisodesInfo.toEpisodeEntity(): EpisodeEntity {
    return EpisodeEntity(
        episodeId = id,
        name = name,
        airDate = airDate,
        created = created,
        episode = episode,
        url = url
    )
}

fun EpisodeEntity.toEpisodesInfo(): EpisodesInfo {
    return EpisodesInfo(
        id = episodeId,
        name = name,
        airDate = airDate,
        characters = emptyList(),
        created = created,
        episode = episode,
        url = url
    )
}