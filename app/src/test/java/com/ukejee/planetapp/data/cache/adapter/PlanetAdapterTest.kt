package com.ukejee.planetapp.data.cache.adapter

import com.ukejee.planetapp.data.planet.api.model.ApiPlanet
import com.ukejee.planetapp.data.planet.api.model.FetchAllPlanetsApiResponse
import com.ukejee.planetapp.data.planet.cache.adapter.PlanetAdapter
import com.ukejee.planetapp.data.planet.cache.model.Planet
import org.junit.Assert
import org.junit.Test
import java.net.URI

class PlanetAdapterTest {

    @Test
    fun `given url when parsed with URI class returns user parameter`() {
        val uri = URI("https://swapi.dev/api/planets/?page=2&hits=90")
        val pageFromUrl = PlanetAdapter.findParameterValue(uri, "page")
        val hitFromUrl = PlanetAdapter.findParameterValue(uri, "hits")

        Assert.assertEquals("2", pageFromUrl)
        Assert.assertEquals("90", hitFromUrl)
        Assert.assertEquals(uri.scheme, "https")
        Assert.assertEquals(uri.host, "swapi.dev")
    }

    @Test
    fun `given a FetchAllPlanetsApiResponse object a list of Planet is returned`() {
        val apiResponse = FetchAllPlanetsApiResponse(
            count = 60,
            next = "https://swapi.dev/api/planets/?page=2",
            previous = null,
            results = listOf(
                ApiPlanet(
                    name = "Endor",
                    rotationPeriod = "18",
                    orbitalPeriod = "402",
                    diameter = "4900",
                    climate = "temperate",
                    gravity = "0.85 standard",
                    terrain = "forests, mountains, lakes",
                    surfaceWater = "8",
                    population = "30000000",
                    residents = listOf(
                        "https://swapi.dev/api/people/30/"
                    ),
                    films = listOf(
                        "https://swapi.dev/api/films/3/"
                    ),
                    created = "2014-12-10T11:50:29.349000Z",
                    edited = "2014-12-20T20:58:18.429000Z",
                    url = "https://swapi.dev/api/planets/7/"
                )
            )
        )

        val expected = listOf(Planet(
            name = "Endor",
            rotationPeriod = "18",
            orbitalPeriod = "402",
            diameter = "4900",
            climate = "temperate",
            gravity = "0.85 standard",
            terrain = "forests, mountains, lakes",
            surfaceWater = "8",
            population = "30000000",
            page = 1
            )
        )

        Assert.assertEquals(expected, PlanetAdapter.toPlanets(apiResponse))
    }
}