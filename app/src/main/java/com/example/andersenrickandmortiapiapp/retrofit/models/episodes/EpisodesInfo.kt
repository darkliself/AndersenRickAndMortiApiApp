package com.example.andersenrickandmortiapiapp.retrofit.models.episodes

import com.google.gson.annotations.SerializedName

data class EpisodesInfo(
    @SerializedName("air_date")
    val airDate: String,
    var characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)