package com.ukejee.planetapp.data.planet.api.endpoint

import com.ukejee.planetapp.data.planet.api.model.FetchAllPlanetsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanetEndpoint {

    @GET("planets/")
    suspend fun fetchAllPlanets(
        @Query("page") page: Int
    ): FetchAllPlanetsApiResponse
}