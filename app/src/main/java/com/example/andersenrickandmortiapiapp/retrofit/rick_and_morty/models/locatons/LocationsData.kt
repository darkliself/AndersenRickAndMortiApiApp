package com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.locatons

import com.google.gson.annotations.SerializedName

data class LocationsData(
    val info: Info,
    @SerializedName("results")
    val locationsList: List<LocationInfo>
)