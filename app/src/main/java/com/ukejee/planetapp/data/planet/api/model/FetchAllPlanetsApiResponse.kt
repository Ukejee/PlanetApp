package com.ukejee.planetapp.data.planet.api.model


data class FetchAllPlanetsApiResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ApiPlanet>
)

