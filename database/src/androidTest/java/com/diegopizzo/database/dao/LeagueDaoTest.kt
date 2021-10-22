package com.diegopizzo.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.diegopizzo.database.config.AppDatabase
import com.diegopizzo.database.entity.LeagueEntity
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
    fun writeLeagueAndReadInList() {
        val league = LeagueEntity(1, "name", "logo.it")
        leagueDao.insertLeagues(league)
        val actualValue = leagueDao.getLeagueByName("name")
        assertEquals(actualValue, league)
    }
}