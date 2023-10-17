package com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes.episodes_list

import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.Info
import com.google.gson.annotations.SerializedName

data class Episodes(
    val info: Info,
    @SerializedName("results")
    val listOfEpisodes: List<EpisodesInfo>
)