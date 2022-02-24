package com.diegopizzo.network.creator

import com.diegopizzo.network.creator.event.EventModelCreator
import com.diegopizzo.network.model.*
import com.diegopizzo.network.model.EventStatistics.StatisticsType.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EventModelCreatorTest {

    private lateinit var creator: EventModelCreator

    @Before
    fun setUp() {
        creator = EventModelCreator()
    }

    @Test
    fun toEventDataModel_dataModelCreated_assertEqualsTrue() {
        val actual = creator.toEventDataModel(model, statisticsModel)
        assertEquals(dataModel, actual)
    }

    companion object {
        private val model = EventModel(
            listOf(
                ResponseEvents(
                    Fixture(
                        1, "UTC", "2021-10-02T18:45:00+00:00",
                        Status("Full time", "FT", 90)
                    ),
                    LeagueDetails(2, "League", "www.league.it", 2021, "Round 4"),
                    Teams(
                        Home(517, "Venezia", "logoTeam3.it"),
                        Away(518, "AwayTeam", "logoTeam4.it")
                    ),
                    Goals(1, 1),
                    listOf(
                        Event(
                            Time(36, null),
                            TeamEvent(
                                517,
                                "Venezia",
                                "logoTeam3.it"
                            ),
                            Player(30752, "S. Kiyine"),
                            Assist(48406, "D. Črnigoj"),
                            EventType.SUBSTITUTION,
                            EventTypeDetail.SUBSTITUTION_1
                        ),
                        Event(
                            Time(90, 2),
                            TeamEvent(
                                517,
                                "Venezia",
                                "logoTeam3.it"
                            ),
                            Player(30752, "S. Kiyine"),
                            Assist(48406, "D. Črnigoj"),
                            EventType.GOAL,
                            EventTypeDetail.NORMAL_GOAL
                        )
                    )
                )
            )
        )

        private val statisticsModel = StatisticsModel(
            listOf(
                TeamStatistics(
                    Team("497", "AS Roma"),
                    listOf(
                        Statistics("Shots on Goal", "3"),
                        Statistics("Shots off Goal", "3"),
                        Statistics("Total Shots", "7"),
                        Statistics("Blocked Shots", "1"),
                        Statistics("Shots insidebox", "5"),
                        Statistics("Shots outsidebox", "2"),
                        Statistics("Fouls", "18"),
                        Statistics("Corner Kicks", "4"),
                        Statistics("Offsides", "1"),
                        Statistics("Ball Possession", "58%"),
                        Statistics("Yellow Cards", "3"),
                        Statistics("Red Cards", null),
                        Statistics("Goalkeeper Saves", "1"),
                        Statistics("Total passes", "495"),
                        Statistics("Passes accurate", "374"),
                        Statistics("Passes %", "76%")
                    )
                ), TeamStatistics(
                    Team("504", "Verona"),
                    listOf(
                        Statistics("Shots on Goal", "3"),
                        Statistics("Shots off Goal", null),
                        Statistics("Total Shots", "5"),
                        Statistics("Blocked Shots", "2"),
                        Statistics("Shots insidebox", "3"),
                        Statistics("Shots outsidebox", "2"),
                        Statistics("Fouls", "14"),
                        Statistics("Corner Kicks", "2"),
                        Statistics("Offsides", "3"),
                        Statistics("Ball Possession", "42%"),
                        Statistics("Yellow Cards", "2"),
                        Statistics("Red Cards", null),
                        Statistics("Goalkeeper Saves", "1"),
                        Statistics("Total passes", "375"),
                        Statistics("Passes accurate", "272"),
                        Statistics("Passes %", "73%")
                    )
                )
            )
        )

        private val statisticsDataModel = listOf(
            EventStatistics(497, 504, SHOTS_ON_GOAL, "3", "3", 0.5f, 0.5f),
            EventStatistics(497, 504, SHOTS_OFF_GOAL, "3", "0", 1.0f, 0.0f),
            EventStatistics(497, 504, TOTAL_SHOTS, "7", "5", 0.59f, 0.42f),
            EventStatistics(497, 504, BLOCKED_SHOTS, "1", "2", 0.34f, 0.67f),
            EventStatistics(497, 504, SHOTS_INSIDE_BOX, "5", "3", 0.63f, 0.38f),
            EventStatistics(497, 504, SHOTS_OUTSIDE_BOX, "2", "2", .5f, .5f),
            EventStatistics(497, 504, FOULS, "18", "14", 0.57f, 0.44f),
            EventStatistics(497, 504, CORNER_KICKS, "4", "2", 0.67f, 0.34f),
            EventStatistics(497, 504, OFFSIDES, "1", "3", 0.25f, 0.75f),
            EventStatistics(497, 504, BALL_POSSESSION, "58%", "42%", 0.58f, 0.42f),
            EventStatistics(497, 504, YELLOW_CARDS, "3", "2", 0.61f, 0.41f),
            EventStatistics(497, 504, RED_CARDS, "0", "0", 0.0f, 0.0f),
            EventStatistics(497, 504, GOALKEEPER_SAVES, "1", "1", .5f, .5f),
            EventStatistics(497, 504, TOTAL_PASSES, "495", "375", 0.57f, 0.44f),
            EventStatistics(497, 504, PASSES_ACCURATE, "374", "272", 0.58f, 0.43f),
            EventStatistics(497, 504, PASSES, "76%", "73%", .76f, .73f)
        )

        private val dataModel = EventDataModel(
            1,
            "2021-10-02T18:45:00+00:00",
            StatusValue.MATCH_FINISHED,
            "90",
            517,
            "Venezia",
            518,
            "AwayTeam",
            "logoTeam3.it",
            "logoTeam4.it",
            "1",
            "1",
            listOf(
                SingleEvent(
                    "36′",
                    517,
                    "S. Kiyine",
                    "D. Črnigoj",
                    EventType.SUBSTITUTION,
                    EventTypeDetail.SUBSTITUTION_1
                ),
                SingleEvent(
                    "90′+2′",
                    517,
                    "S. Kiyine",
                    "D. Črnigoj",
                    EventType.GOAL,
                    EventTypeDetail.NORMAL_GOAL
                )
            ), statisticsDataModel
        )
    }
}