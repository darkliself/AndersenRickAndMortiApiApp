package com.example.andersenrickandmortiapiapp.data.mappers

import com.example.andersenrickandmortiapiapp.data.room.model.entity.LocationEntity
import com.example.andersenrickandmortiapiapp.retrofit.models.locatons.LocationInfo


fun LocationInfo.toLocationEntity(): LocationEntity {
    return LocationEntity(
        locationId = id,
        name = name,
        dimension = dimension,
        created = created,
        type = type,
        url = url
    )
}

fun LocationEntity.toLocationInfo(): LocationInfo {
    return LocationInfo(
        id = locationId,
        name = name,
        dimension = dimension,
        residents = emptyList(),
        created = created,
        type = type,
        url = url
    )
}
