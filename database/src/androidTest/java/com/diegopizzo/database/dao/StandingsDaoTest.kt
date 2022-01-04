package com.diegopizzo.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.diegopizzo.database.config.AppDatabase
import com.diegopizzo.database.entity.StandingsEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StandingsDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: StandingsDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = database.standingsDao()
    }

    @Test
    fun writeStandingsAndReadInList() {
        dao.insertStandings(*standings.toTypedArray())
        val actualValue = dao.getStandingsByLeagueId(LEAGUE_ID)
        assertEquals(actualValue, standings)
    }

    companion object {
        private const val LEAGUE_ID = 135L
        private val standings = listOf(
            StandingsEntity(
                "505",
                "Inter",
                "https://media.api-sports.io/football/teams/505.png",
                "1",
                "46",
                "34",
                "WWWWW",
                "19",
                "14",
                "4",
                "1",
                "49",
                "15",
                LEAGUE_ID
            ),
            StandingsEntity(
                "489",
                "AC Milan",
                "https://media.api-sports.io/football/teams/489.png",
                "2",
                "42",
                "18",
                "WLDWW",
                "19",
                "13",
                "3",
                "3",
                "40",
                "22",
                LEAGUE_ID
            ),
            StandingsEntity(
                "492",
                "Napoli",
                "https://media.api-sports.io/football/teams/492.png",
                "3",
                "39",
                "21",
                "LWLLD",
                "19",
                "12",
                "3",
                "4",
                "35",
                "14",
                LEAGUE_ID
            )
        )
    }
}