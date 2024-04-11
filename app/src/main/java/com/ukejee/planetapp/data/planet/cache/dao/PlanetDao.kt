package com.ukejee.planetapp.data.planet.cache.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ukejee.planetapp.data.planet.cache.model.Planet

@Dao
interface PlanetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(planets: List<Planet>)

    @Query("Select * From planets Order By page")
    fun getPlanets(): PagingSource<Int, Planet>

    @Query("Delete From planets")
    suspend fun clearAllPlanets()
}