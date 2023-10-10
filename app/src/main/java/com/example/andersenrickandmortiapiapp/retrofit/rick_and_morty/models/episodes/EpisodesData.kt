package com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes

import com.google.gson.annotations.SerializedName

data class EpisodesData(
    val info: Info,
    @SerializedName("results")
    val listOfEpisodes: List<EpisodesInfo>
)