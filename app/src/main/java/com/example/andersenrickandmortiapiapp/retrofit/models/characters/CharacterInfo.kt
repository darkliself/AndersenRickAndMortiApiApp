package com.example.andersenrickandmortiapiapp.retrofit.models.characters

data class CharacterInfo(
    val created: String,
    var episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)