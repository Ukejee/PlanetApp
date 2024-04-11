package com.ukejee.planetapp.ui.planet

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ukejee.planetapp.data.planet.cache.model.Planet
import com.ukejee.planetapp.domain.PlanetsRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class PlanetViewModel @Inject constructor(
    private val planetsRemoteMediator: PlanetsRemoteMediator
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    fun getPlanets(): Flow<PagingData<Planet>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 8,
                initialLoadSize = 10,
                maxSize = 60
            ),
            pagingSourceFactory = {
                planetsRemoteMediator.getPlanets()
            },
            remoteMediator = planetsRemoteMediator
        ).flow

    val selectedPlanet = MutableStateFlow<Planet?>(null)

    var onPlanetClicked: (() -> Unit)? = null

    var onBackPressed: (() -> Unit)? = null
}