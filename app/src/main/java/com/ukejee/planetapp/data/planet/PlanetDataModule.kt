package com.ukejee.planetapp.data.planet

import com.ukejee.planetapp.data.AppDatabase
import com.ukejee.planetapp.data.planet.api.endpoint.PlanetEndpoint
import com.ukejee.planetapp.data.planet.datasource.PlanetLocalDataSource
import com.ukejee.planetapp.data.planet.datasource.PlanetRemoteDataSource
import com.ukejee.planetapp.data.planet.datasource.PlanetRetrofitDataSource
import com.ukejee.planetapp.data.planet.datasource.PlanetRoomDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object PlanetDataModule {

    @Provides
    fun providePlanetEndpoint(retrofit: Retrofit): PlanetEndpoint {
        return retrofit.create(PlanetEndpoint::class.java)
    }

    @Provides
    fun providePlanetNetworkDataSource(planetEndpoint: PlanetEndpoint): PlanetRemoteDataSource {
        return PlanetRetrofitDataSource(planetEndpoint)
    }

    @Provides
    fun provideProductLocalDataSource(appDatabase: AppDatabase): PlanetLocalDataSource {
        return PlanetRoomDataSource(appDatabase)
    }
}