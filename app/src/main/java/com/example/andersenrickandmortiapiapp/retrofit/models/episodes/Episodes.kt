package com.example.andersenrickandmortiapiapp.retrofit.models.episodes

import com.example.andersenrickandmortiapiapp.retrofit.models.Info
import com.google.gson.annotations.SerializedName

data class Episodes(
    val info: Info,
    @SerializedName("results")
    val listOfEpisodes: List<EpisodesInfo>
)