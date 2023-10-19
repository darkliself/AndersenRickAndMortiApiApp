package com.example.andersenrickandmortiapiapp.data.mappers

import com.example.andersenrickandmortiapiapp.data.room.model.entity.CharacterEntity
import com.example.andersenrickandmortiapiapp.retrofit.models.characters.CharacterInfo
import com.example.andersenrickandmortiapiapp.retrofit.models.characters.Location
import com.example.andersenrickandmortiapiapp.retrofit.models.characters.Origin


fun CharacterInfo.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        characterId = id,
        name = name,
        created = created,
        gender = gender,
        image = image,
        locationName = location.name,
        location = location.url,
        origin = origin.url,
        originName = origin.name,
        species = species,
        status = status,
        type = type,
        url = url
    )
}

fun CharacterEntity.toCharacterInfo(): CharacterInfo {
    return CharacterInfo(
        id = characterId,
        name = name,
        created = created,
        episode = emptyList(),
        gender = gender,
        image = image,
        location = Location(
            name = locationName,
            url = location
        ),
        origin = Origin(
            name = originName,
            url = origin
        ),
        species = species,
        status = status,
        type = type,
        url = url
    )
}


