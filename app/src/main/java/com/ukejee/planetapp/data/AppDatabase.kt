package com.ukejee.planetapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ukejee.planetapp.data.planet.cache.dao.PlanetDao
import com.ukejee.planetapp.data.planet.cache.dao.RemoteKeysDao
import com.ukejee.planetapp.data.planet.cache.model.CachedPlanet
import com.ukejee.planetapp.data.planet.cache.model.RemoteKeys

@Database(
    entities = [CachedPlanet::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getPlanetDao(): PlanetDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}