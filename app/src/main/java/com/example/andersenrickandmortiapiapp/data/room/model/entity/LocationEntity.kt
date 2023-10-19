package com.example.andersenrickandmortiapiapp.data.room.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
data class LocationEntity(
    @PrimaryKey
    @ColumnInfo(name = "location_id")
    val locationId: Int,
    val created: String,
    val dimension: String,
    val name: String,
    val type: String,
    val url: String
)
