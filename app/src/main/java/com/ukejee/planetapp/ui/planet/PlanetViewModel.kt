package com.ukejee.planetapp.ui.planet

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ukejee.planetapp.data.planet.cache.model.CachedPlanet
import com.ukejee.planetapp.domain.PlanetsRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PlanetViewModel @Inject constructor(
    private val planetsRemoteMediator: PlanetsRemoteMediator
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    fun getPlanets(): Flow<PagingData<CachedPlanet>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 10,
                initialLoadSize = 10,
            ),
            pagingSourceFactory = {
                planetsRemoteMediator.getPlanets()
            },
            remoteMediator = planetsRemoteMediator
        ).flow
}