package com.ukejee.planetapp.data.planet.datasource

import com.ukejee.planetapp.data.planet.api.endpoint.PlanetEndpoint
import com.ukejee.planetapp.data.planet.api.model.FetchAllPlanetsApiResponse

class PlanetRetrofitDataSource(
    private val planetEndpoint: PlanetEndpoint
): PlanetRemoteDataSource {

    override suspend fun getAllPlanets(page: Int): FetchAllPlanetsApiResponse {
        return planetEndpoint.fetchAllPlanets(page)
    }
}