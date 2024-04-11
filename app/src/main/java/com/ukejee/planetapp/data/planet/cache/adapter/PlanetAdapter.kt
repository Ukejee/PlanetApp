package com.ukejee.planetapp.data.planet.cache.adapter

import com.ukejee.planetapp.data.planet.api.model.FetchAllPlanetsApiResponse
import com.ukejee.planetapp.data.planet.cache.model.Planet
import java.net.URI

object PlanetAdapter {

    fun toPlanets(apiResponse: FetchAllPlanetsApiResponse): List<Planet> {
        val currentPage = if (!apiResponse.next.isNullOrEmpty()) {
            val nextPage = findParameterValue(URI(apiResponse.next), "page")?.toInt()
            nextPage?.minus(1)
        } else {
            val previousPage = findParameterValue(URI(apiResponse.previous), "page")?.toInt()
            previousPage?.plus(1)
        }

        return apiResponse.results.map {
            Planet(
                climate = it.climate,
                diameter = it.diameter,
                gravity = it.gravity,
                name = it.name,
                orbitalPeriod = it.orbitalPeriod,
                population = it.population,
                rotationPeriod = it.rotationPeriod,
                surfaceWater = it.surfaceWater,
                terrain = it.terrain,
                page = currentPage ?: 1
            )
        }
    }

    fun findParameterValue(uri: URI, parameterName: String): String? {
        return uri.rawQuery.split('&').map {
            val parts = it.split('=')
            val name = parts.firstOrNull() ?: ""
            val value = parts.drop(1).firstOrNull() ?: ""
            Pair(name, value)
        }.firstOrNull { it.first == parameterName }?.second
    }
}