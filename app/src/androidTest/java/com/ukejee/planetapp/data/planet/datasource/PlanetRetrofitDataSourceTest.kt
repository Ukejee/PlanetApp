package com.ukejee.planetapp.data.planet.datasource

import androidx.test.platform.app.InstrumentationRegistry
import com.ukejee.planetapp.application.PlanetApplication
import com.ukejee.planetapp.data.planet.api.endpoint.PlanetEndpoint
import com.ukejee.planetapp.data.planet.api.model.ApiPlanet
import com.ukejee.planetapp.data.planet.api.model.FetchAllPlanetsApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class ProductRetrofitDataSourceTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(PlanetEndpoint::class.java)

    private val sut = PlanetRetrofitDataSource(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun fromGetAllPlanets_with200Response_returnsProperlyMappedObject() {
        mockWebServer.enqueueResponse("testResponse.json", 200)

        runBlocking {
            val actual = sut.getAllPlanets(1)

            val expected = FetchAllPlanetsApiResponse(
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

            assertEquals(expected, actual)
        }
    }

}


internal fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
    val context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as PlanetApplication
    val inputStream = context.assets.open("networkResponses/$fileName")

    val source = inputStream?.let { inputStream.source().buffer() }
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }
}