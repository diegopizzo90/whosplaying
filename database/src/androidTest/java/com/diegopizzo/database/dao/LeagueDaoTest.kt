package com.diegopizzo.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.diegopizzo.database.config.AppDatabase
import com.diegopizzo.database.entity.LeagueEntity
import com.diegopizzo.network.interactor.league.LeagueType.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LeagueDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var leagueDao: LeagueDao

    companion object {
        private val serieAEntity = LeagueEntity(
            1,
            "Serie A",
            "logo.it",
            "Italy",
            "IT",
            LEAGUE
        )
        private val leaguesToSave = listOf(
            LeagueEntity(1, "Serie A", "logo.it", "Italy", "IT", LEAGUE),
            LeagueEntity(2, "Coppa Italia", "logo.it", "Italy", "IT", CUP)
        )
    }

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        leagueDao = database.leagueDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun getLeagueByName_writeLeagueAndReadInList() {
        leagueDao.insertLeagues(*leaguesToSave.toTypedArray())
        val actualValue = leagueDao.getLeagueByName("Serie A")
        assertEquals(actualValue, serieAEntity)
    }

    @Test
    fun getLeagueByCountry_writeLeagueAndReadInList() {
        leagueDao.insertLeagues(*leaguesToSave.toTypedArray())
        val actualValue = leagueDao.getLeaguesByCountry("IT")
        assertEquals(actualValue, leaguesToSave)
    }
}