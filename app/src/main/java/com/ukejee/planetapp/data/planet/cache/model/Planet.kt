package com.ukejee.planetapp.data.planet.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planets")
data class Planet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val climate: String,
    val diameter: String,
    val gravity: String,
    val name: String,
    val orbitalPeriod: String,
    val population: String,
    val rotationPeriod: String,
    val surfaceWater: String,
    val terrain: String,
    @ColumnInfo(name = "page")
    var page: Int,
)
