package com.ukejee.planetapp.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ukejee.planetapp.data.planet.cache.adapter.CachedPlanetAdapter
import com.ukejee.planetapp.data.planet.cache.model.CachedPlanet
import com.ukejee.planetapp.data.planet.cache.model.RemoteKeys
import com.ukejee.planetapp.data.planet.datasource.PlanetLocalDataSource
import com.ukejee.planetapp.data.planet.datasource.PlanetRemoteDataSource
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PlanetsRemoteMediator (
    private val planetNetworkDataSource: PlanetRemoteDataSource,
    private val planetLocalDataSource: PlanetLocalDataSource
) : RemoteMediator<Int, CachedPlanet>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (planetLocalDataSource.getCreationTime() ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CachedPlanet>
    ): MediatorResult {
        val page: Int = when(loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val apiResponse = planetNetworkDataSource.getAllPlanets(page)


            val planets = CachedPlanetAdapter.toCachedPlanets(apiResponse)
            val endOfPaginationReached = apiResponse.next.isNullOrEmpty() //planets.isEmpty()


            if (loadType == LoadType.REFRESH) {
                planetLocalDataSource.removeAllRemoteKeys()
                planetLocalDataSource.removeAllPlanets()
            }

            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (endOfPaginationReached) null else page + 1
            val remoteKeys = planets.map {
                RemoteKeys(planetName = it.name, prevKey = prevKey, currentPage = page, nextKey = nextKey)
            }

            planetLocalDataSource.saveRemoteKeys(remoteKeys)
            planetLocalDataSource.savePlanets(planets.onEachIndexed { _, cachedPlanet -> cachedPlanet.page = page })


            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: Exception) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CachedPlanet>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { name ->
                planetLocalDataSource.getRemoteKey(name)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CachedPlanet>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { planet ->
            planetLocalDataSource.getRemoteKey(planet.name)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CachedPlanet>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { planet ->
            planetLocalDataSource.getRemoteKey(planet.name)
        }
    }

    fun getPlanets(): PagingSource<Int, CachedPlanet> {
        return planetLocalDataSource.getPlanets()
    }
}