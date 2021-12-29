package com.diegopizzo.network.creator

import com.diegopizzo.network.creator.standing.StandingModelCreator
import com.diegopizzo.network.model.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class StandingModelCreatorTest {
    private lateinit var creator: StandingModelCreator

    @Before
    fun setUp() {
        creator = StandingModelCreator()
    }

    @Test
    fun fromModelToDataModel_dataModelCreated_assertEqualsTrue() {
        val actualValue = creator.fromModelToDataModel(model)
        assertEquals(actualValue, dataModel)
    }

    companion object {
        private val model = StandingModel(
            response = listOf(
                ResponseStanding(
                    LeagueData(
                        listOf(
                            listOf(
                                Standing(
                                    "1",
                                    team = TeamEvent(
                                        505,
                                        "Inter",
                                        "https://media.api-sports.io/football/teams/505.png"
                                    ),
                                    "46",
                                    "34",
                                    "WWWWW",
                                    all = AllStandingInfo(
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
                                Standing(
                                    "2",
                                    team = TeamEvent(
                                        489,
                                        "AC Milan",
                                        "https://media.api-sports.io/football/teams/489.png"
                                    ),
                                    "42",
                                    "18",
                                    "WLDWW",
                                    all = AllStandingInfo(
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
                                Standing(
                                    "3",
                                    team = TeamEvent(
                                        492,
                                        "Napoli",
                                        "https://media.api-sports.io/football/teams/492.png"
                                    ),
                                    "39",
                                    "21",
                                    "LWLLD",
                                    all = AllStandingInfo(
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
        StandingDataModel(
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
        StandingDataModel(
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
        StandingDataModel(
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