package com.ukejee.planetapp.data.cache.adapter

import com.ukejee.planetapp.data.planet.cache.adapter.CachedPlanetAdapter
import org.junit.Assert
import org.junit.Test
import java.net.URI

class CachedPlanetAdapterTest {

    @Test
    fun `given url when parsed with URI class returns user parameter`() {
        val uri = URI("https://swapi.dev/api/planets/?page=2&hits=90")
        val pageFromUrl = CachedPlanetAdapter.findParameterValue(uri, "page")
        val hitFromUrl = CachedPlanetAdapter.findParameterValue(uri, "hits")

        Assert.assertEquals("2", pageFromUrl)
        Assert.assertEquals("90", hitFromUrl)
        Assert.assertEquals(uri.scheme, "https")
        Assert.assertEquals(uri.host, "swapi.dev")
    }
}