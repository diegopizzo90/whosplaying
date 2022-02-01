package com.diegopizzo.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.diegopizzo.database.config.AppDatabase
import com.diegopizzo.database.entity.FixtureEntity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FixtureDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var fixtureDao: FixtureDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        fixtureDao = database.fixtureDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun writeFixturesAndReadInList() {
        fixtureDao.insertFixture(*fixtures.toTypedArray())
        val from =
            LocalDate.parse("2021-10-01", DateTimeFormatter.ISO_DATE).atStartOfDay(ZoneOffset.UTC)
        val to =
            LocalDate.parse("2021-10-01", DateTimeFormatter.ISO_DATE).atStartOfDay(ZoneOffset.UTC)
                .with(LocalTime.MAX)
        val actualValue = fixtureDao.getFixturesByLeagueId(1, from, to)
        assertEquals(actualValue, fixturesFromQuery)
    }

    companion object {
        private val fixtures = listOf(
            FixtureEntity(
                11,
                ZonedDateTime.parse(
                    "2021-10-01T17:45:00+00:00",
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
                ),
                "1H",
                "54",
                "team5",
                "team6",
                "logoTeam5.it",
                "logoTeam6.it",
                "1",
                "1",
                1
            ),
            FixtureEntity(
                22,
                ZonedDateTime.parse(
                    "2021-10-01T17:45:00+00:00",
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
                ),
                "NS",
                null,
                "team3",
                "team4",
                "logoTeam3.it",
                "logoTeam4.it",
                "",
                "",
                1
            ),
            FixtureEntity(
                33,
                ZonedDateTime.parse(
                    "2021-10-02T17:45:00+00:00",
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
                ),
                "NS",
                null,
                "team1",
                "team2",
                "logoTeam1.it",
                "logoTeam2.it",
                "",
                "",
                1
            )
        )

        private val fixturesFromQuery = listOf(
            FixtureEntity(
                11,
                ZonedDateTime.parse(
                    "2021-10-01T17:45:00+00:00",
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
                ),
                "1H",
                "54",
                "team5",
                "team6",
                "logoTeam5.it",
                "logoTeam6.it",
                "1",
                "1",
                1
            ),
            FixtureEntity(
                22,
                ZonedDateTime.parse(
                    "2021-10-01T17:45:00+00:00",
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
                ),
                "NS",
                null,
                "team3",
                "team4",
                "logoTeam3.it",
                "logoTeam4.it",
                "",
                "",
                1
            )
        )
    }
}