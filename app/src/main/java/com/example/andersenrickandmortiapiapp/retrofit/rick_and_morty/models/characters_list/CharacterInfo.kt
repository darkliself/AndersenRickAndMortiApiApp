package com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters_list

data class CharacterInfo(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: CharacterLocation,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)