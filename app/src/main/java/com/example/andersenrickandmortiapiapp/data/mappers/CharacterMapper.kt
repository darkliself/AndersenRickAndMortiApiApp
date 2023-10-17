package com.example.andersenrickandmortiapiapp.data.mappers

import com.example.andersenrickandmortiapiapp.data.room.model.CharacterEntity
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters.character_list.CharacterInfo
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters.character_list.Location
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters.character_list.Origin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun CharacterInfo.toCharacterEntity(): CharacterEntity {
    val gson = Gson()
    return CharacterEntity(
        id = id,
        name = name,
        created = created,
        episode = gson.toJson(episode),
        gender = gender,
        image = image,
        location_name = location.name,
        location = location.url,
        origin = origin.url,
        origin_name = origin.name,
        species = species,
        status = status,
        type = type,
        url = url
    )
}

fun CharacterEntity.toCharacterInfo(): CharacterInfo {
    val gson = Gson()
    val listType = object : TypeToken<List<String>>() {}.type
    return CharacterInfo(
        id = id,
        name = name,
        created = created,
        episode = gson.fromJson(episode, listType),
        gender = gender,
        image = image,
        location = Location(
            name = location_name,
            url = location
        ),
        origin = Origin(
            name = origin_name,
            url = location
        ),
        species = species,
        status = status,
        type = type,
        url = url
    )
}
