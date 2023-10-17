package com.example.andersenrickandmortiapiapp.data.mappers

import com.example.andersenrickandmortiapiapp.data.room.model.LocationEntity
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.locatons.LocationInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun LocationInfo.toLocationEntity(): LocationEntity {
    val gson = Gson()
    return LocationEntity(
        id = id,
        name = name,
        dimension = dimension,
        created = created,
        residents = gson.toJson(residents),
        type = type,
        url = url
    )
}

fun LocationEntity.toLocationInfo(): LocationInfo {
    val gson = Gson()
    val listType = object : TypeToken<List<String>>() {}.type
    return LocationInfo(
        id = id,
        name = name,
        dimension = dimension,
        residents = gson.fromJson(residents, listType),
        created = created,
        type = type,
        url = url
    )
}
