package com.example.andersenrickandmortiapiapp.retrofit.models.characters

import com.example.andersenrickandmortiapiapp.retrofit.models.Info
import com.google.gson.annotations.SerializedName

data class Characters(
    val info: Info,
    @SerializedName("results")
    val listOfCharacters: List<CharacterInfo>
)