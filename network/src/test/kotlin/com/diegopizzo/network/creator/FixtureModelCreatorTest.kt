package com.diegopizzo.network.creator

import com.diegopizzo.network.model.*
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class FixtureModelCreatorTest {

    private val creator = FixtureModelCreator()

    @Test
    fun convertUtcDateTimeToLocalTime_dateConverted_assertEqualsTrue() {
        val date = creator.convertUtcDateTimeToLocalTime("2021-10-01T18:45:00+00:00")
        assertEquals("19:45", date)
    }

    @Test
    fun convertUtcDateTimeToLocalDate_dateConverted_assertEqualsTrue() {
        val date = creator.convertUtcDateTimeToLocalDate("2021-10-01T18:45:00+00:00")
        assertEquals("Fri, 1 Oct 2021", date)
    }

    @Test
    fun toFixturesDataModel_createDataModel_assertEqualsTrue() {
        val actual = creator.toFixturesDataModel(Response.success(model))
        assertEquals(dataModel, actual)
    }


    companion object {
        private val model = FixtureModel(
            listOf(
                ResponseFixture(
                    Fixture(1,"UTC", "2021-10-03T18:45:00+00:00", Status("NS", null)),
                    Teams(Home("team1", "logoTeam1.it"), Away("team2", "logoTeam2.it")),
                    Goals(null, null)
                ),
                ResponseFixture(
                    Fixture(1,"UTC", "2021-10-02T18:45:00+00:00", Status("NS", null)),
                    Teams(Home("team3", "logoTeam3.it"), Away("team4", "logoTeam4.it")),
                    Goals(null, null)
                ),
                ResponseFixture(
                    Fixture(1,"UTC", "2021-10-01T18:45:00+00:00", Status("1H", 54)),
                    Teams(Home("team5", "logoTeam5.it"), Away("team6", "logoTeam6.it")),
                    Goals(1, 1)
                )
            )
        )

        private val dataModel = listOf(
            FixtureDataModel(
                1,
                "Fri, 1 Oct 2021",
                "19:45",
                "1H",
                "54",
                "team5",
                "team6",
                "logoTeam5.it",
                "logoTeam6.it",
                "1",
                "1"
            ),
            FixtureDataModel(
                1,
                "Sat, 2 Oct 2021",
                "19:45",
                "NS",
                null,
                "team3",
                "team4",
                "logoTeam3.it",
                "logoTeam4.it",
                "",
                ""
            ),
            FixtureDataModel(
                1,
                "Sun, 3 Oct 2021",
                "19:45",
                "NS",
                null,
                "team1",
                "team2",
                "logoTeam1.it",
                "logoTeam2.it",
                "",
                ""
            )
        )
    }
}