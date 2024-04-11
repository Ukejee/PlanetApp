package com.ukejee.planetapp.domain

import com.ukejee.planetapp.data.planet.datasource.PlanetLocalDataSource
import com.ukejee.planetapp.data.planet.datasource.PlanetRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlanetsRemoteMediatorModule {

    @Provides
    @Singleton
    fun providePlanetsRemoteMediator(
        planetLocalDataSource: PlanetLocalDataSource,
        planetNetworkDataSource: PlanetRemoteDataSource
    ): PlanetsRemoteMediator {
        return PlanetsRemoteMediator(planetNetworkDataSource, planetLocalDataSource)
    }

}