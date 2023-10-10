package com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes

import com.google.gson.annotations.SerializedName

data class EpisodesInfo(
    @SerializedName("air_date")
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)