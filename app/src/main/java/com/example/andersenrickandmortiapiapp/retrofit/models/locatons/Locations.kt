package com.example.andersenrickandmortiapiapp.retrofit.models.locatons

import com.example.andersenrickandmortiapiapp.retrofit.models.Info
import com.google.gson.annotations.SerializedName

data class Locations(
    val info: Info,
    @SerializedName("results")
    val locationsList: List<LocationInfo>
)