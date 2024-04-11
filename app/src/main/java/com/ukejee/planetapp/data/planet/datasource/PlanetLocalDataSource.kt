package com.ukejee.planetapp.data.planet.datasource

import androidx.paging.PagingSource
import com.ukejee.planetapp.data.planet.cache.model.Planet
import com.ukejee.planetapp.data.planet.cache.model.RemoteKeys

interface PlanetLocalDataSource {

    suspend fun savePlanets(planets: List<Planet>)

    fun getPlanets(): PagingSource<Int, Planet>

    suspend fun removeAllPlanets()

    suspend fun saveRemoteKeys(remoteKeys: List<RemoteKeys>)

    suspend fun removeAllRemoteKeys()

    suspend fun getCreationTime(): Long?

    suspend fun getRemoteKey(name: String): RemoteKeys?
}