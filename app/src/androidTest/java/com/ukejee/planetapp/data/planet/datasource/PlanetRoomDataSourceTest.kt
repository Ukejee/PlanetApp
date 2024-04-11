package com.ukejee.planetapp.data.planet.datasource

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ukejee.planetapp.data.AppDatabase
import com.ukejee.planetapp.data.planet.cache.dao.RemoteKeysDao
import com.ukejee.planetapp.data.planet.cache.model.RemoteKeys
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PlanetRoomDataSourceTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var remoteKeysDao: RemoteKeysDao
    private lateinit var db: AppDatabase


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        remoteKeysDao = db.getRemoteKeysDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun fromPlanetRoomDataSource_inputOneEntry_returnsSameEntry() = runBlocking {
        val remoteKey = RemoteKeys(
            planetName = "Earth",
            prevKey = null,
            currentPage = 1,
            nextKey = null
        )

        val sut = PlanetRoomDataSource(db)
        sut.saveRemoteKeys(listOf(remoteKey))
        val expectedRemoteKey = sut.getRemoteKey("Earth")

        Assert.assertEquals(expectedRemoteKey, remoteKey)
    }
}