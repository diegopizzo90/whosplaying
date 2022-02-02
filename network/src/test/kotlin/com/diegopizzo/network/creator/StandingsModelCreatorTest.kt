package com.diegopizzo.network.creator

import com.diegopizzo.network.creator.standings.StandingsModelCreator
import com.diegopizzo.network.model.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class StandingsModelCreatorTest {
    private lateinit var creator: StandingsModelCreator

    @Before
    fun setUp() {
        creator = StandingsModelCreator()
    }

    @Test
    fun fromModelToDataModel_dataModelCreated_assertEqualsTrue() {
        val actualValue = creator.fromModelToDataModel(model)
        assertEquals(actualValue, dataModel)
    }

    companion object {
        private val model = StandingsModel(
            response = listOf(
                ResponseStandings(
                    LeagueData(
                        listOf(
                            listOf(
                                Standings(
                                    "1",
                                    team = TeamEvent(
                                        505,
                                        "Inter",
                                        "https://media.api-sports.io/football/teams/505.png"
                                    ),
                                    "46",
                                    "34",
                                    "WWWWW",
                                    all = AllStandingsInfo(
                                        "19",
                                        "14",
                                        "4",
                                        "1",
                                        goals = GoalsInfo(
                                            "49",
                                            "15"
                                        )
                                    ),
                                    "2021-12-22T00:00:00+00:00"
                                ),
                                Standings(
                                    "2",
                                    team = TeamEvent(
                                        489,
                                        "AC Milan",
                                        "https://media.api-sports.io/football/teams/489.png"
                                    ),
                                    "42",
                                    "18",
                                    "WLDWW",
                                    all = AllStandingsInfo(
                                        "19",
                                        "13",
                                        "3",
                                        "3",
                                        goals = GoalsInfo(
                                            "40",
                                            "22"
                                        )
                                    ),
                                    "2021-12-22T00:00:00+00:00"
                                ),
                                Standings(
                                    "3",
                                    team = TeamEvent(
                                        492,
                                        "Napoli",
                                        "https://media.api-sports.io/football/teams/492.png"
                                    ),
                                    "39",
                                    "21",
                                    "LWLLD",
                                    all = AllStandingsInfo(
                                        "19",
                                        "12",
                                        "3",
                                        "4",
                                        goals = GoalsInfo(
                                            "35",
                                            "14"
                                        )
                                    ),
                                    "2021-12-22T00:00:00+00:00"
                                )
                            )
                        )
                    )
                )
            )
        )
    }

    private val dataModel = listOf(
        StandingsDataModel(
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
            "15"
        ),
        StandingsDataModel(
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
            "22"
        ),
        StandingsDataModel(
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
            "14"
        )
    )
}