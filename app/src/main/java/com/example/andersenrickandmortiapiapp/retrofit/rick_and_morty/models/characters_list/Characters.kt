package com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters_list

import com.google.gson.annotations.SerializedName

data class Characters(
    val info: Info,
    @SerializedName("results")
    val listOfCharacters: List<CharacterInfo>
)