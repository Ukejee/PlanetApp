package com.ukejee.planetapp.data.planet.datasource

import com.ukejee.planetapp.data.planet.api.model.FetchAllPlanetsApiResponse

interface PlanetRemoteDataSource {

    suspend fun getAllPlanets(page: Int): FetchAllPlanetsApiResponse
}