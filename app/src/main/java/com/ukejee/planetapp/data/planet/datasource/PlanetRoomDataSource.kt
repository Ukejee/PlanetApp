package com.ukejee.planetapp.data.planet.datasource

import androidx.paging.PagingSource
import com.ukejee.planetapp.data.AppDatabase
import com.ukejee.planetapp.data.planet.cache.model.Planet
import com.ukejee.planetapp.data.planet.cache.model.RemoteKeys

class PlanetRoomDataSource(
    private val appDatabase: AppDatabase
) : PlanetLocalDataSource {
    override suspend fun savePlanets(planets: List<Planet>) {
        appDatabase.getPlanetDao().insertAll(planets)
    }

    override fun getPlanets(): PagingSource<Int, Planet> {
        return appDatabase.getPlanetDao().getPlanets()
    }

    override suspend fun removeAllPlanets() {
        appDatabase.getPlanetDao().clearAllPlanets()
    }

    override suspend fun saveRemoteKeys(remoteKeys: List<RemoteKeys>) {
        appDatabase.getRemoteKeysDao().insertAll(remoteKeys)
    }

    override suspend fun removeAllRemoteKeys() {
        appDatabase.getRemoteKeysDao().clearRemoteKeys()
    }

    override suspend fun getCreationTime(): Long? {
        return appDatabase.getRemoteKeysDao().getCreationTime()
    }

    override suspend fun getRemoteKey(name: String): RemoteKeys? {
        return appDatabase.getRemoteKeysDao().getRemoteKeyByPlanetName(name)
    }
}