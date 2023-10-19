package com.example.andersenrickandmortiapiapp.retrofit.models.locatons

data class LocationInfo(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    var residents: List<String>,
    val type: String,
    val url: String
)