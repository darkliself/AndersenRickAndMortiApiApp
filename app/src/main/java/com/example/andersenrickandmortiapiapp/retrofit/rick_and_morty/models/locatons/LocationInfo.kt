package com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.locatons

data class LocationInfo(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)