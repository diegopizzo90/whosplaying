package com.diegopizzo.network.creator

import com.diegopizzo.network.creator.fixture.FixtureModelCreator
import com.diegopizzo.network.model.*
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class FixtureModelCreatorTest {

    private val creator = FixtureModelCreator()

    @Test
    fun toFixturesDataModel_createDataModel_assertEqualsTrue() {
        val actual = creator.toFixturesDataModel(listOf(Response.success(model)))
        assertEquals(dataModel, actual)
    }

    companion object {
        private val model = FixtureModel(
            listOf(
                ResponseFixture(
                    Fixture(1, "UTC", "2021-10-01T18:45:00+00:00", Status("First Half", "1H", 54)),
                    Teams(Home(3, "team5", "logoTeam5.it"), Away(3, "team6", "logoTeam6.it")),
                    Goals(1, 1)
                ),
                ResponseFixture(
                    Fixture(
                        1,
                        "UTC",
                        "2021-10-02T18:45:00+00:00",
                        Status("Not Started", "NS", null)
                    ),
                    Teams(Home(2, "team3", "logoTeam3.it"), Away(2, "team4", "logoTeam4.it")),
                    Goals(null, null)
                ),
                ResponseFixture(
                    Fixture(
                        1,
                        "UTC",
                        "2021-10-03T18:45:00+00:00",
                        Status("Not Started", "NS", null)
                    ),
                    Teams(Home(1, "team1", "logoTeam1.it"), Away(1, "team2", "logoTeam2.it")),
                    Goals(null, null)
                )
            )
        )

        private val dataModel = listOf(
            FixtureDataModel(
                1,
                "2021-10-01T18:45:00+00:00",
                "54′",
                "54",
                "team5",
                "team6",
                "logoTeam5.it",
                "logoTeam6.it",
                "1",
                "1",
                true
            ),
            FixtureDataModel(
                1,
                "2021-10-02T18:45:00+00:00",
                "19:45",
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
                "2021-10-03T18:45:00+00:00",
                "19:45",
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