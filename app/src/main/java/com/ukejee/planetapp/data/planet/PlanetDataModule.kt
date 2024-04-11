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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlanetDataModule {

    @Provides
    @Singleton
    fun providePlanetEndpoint(retrofit: Retrofit): PlanetEndpoint {
        return retrofit.create(PlanetEndpoint::class.java)
    }

    @Provides
    @Singleton
    fun providePlanetRemoteDataSource(planetEndpoint: PlanetEndpoint): PlanetRemoteDataSource {
        return PlanetRetrofitDataSource(planetEndpoint)
    }

    @Provides
    @Singleton
    fun providePlanetLocalDataSource(appDatabase: AppDatabase): PlanetLocalDataSource {
        return PlanetRoomDataSource(appDatabase)
    }
}