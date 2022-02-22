package com.diegopizzo.network.creator

import com.diegopizzo.network.creator.event.EventModelCreator
import com.diegopizzo.network.model.*
import com.diegopizzo.network.model.EventStatistics.StatisticsDataModel
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
            EventStatistics(
                497, listOf(
                    StatisticsDataModel(SHOTS_ON_GOAL, "3"),
                    StatisticsDataModel(SHOTS_OFF_GOAL, "3"),
                    StatisticsDataModel(TOTAL_SHOTS, "7"),
                    StatisticsDataModel(BLOCKED_SHOTS, "1"),
                    StatisticsDataModel(SHOTS_INSIDE_BOX, "5"),
                    StatisticsDataModel(SHOTS_OUTSIDE_BOX, "2"),
                    StatisticsDataModel(FOULS, "18"),
                    StatisticsDataModel(CORNER_KICKS, "4"),
                    StatisticsDataModel(OFFSIDES, "1"),
                    StatisticsDataModel(BALL_POSSESSION, "58%"),
                    StatisticsDataModel(YELLOW_CARDS, "3"),
                    StatisticsDataModel(RED_CARDS, "0"),
                    StatisticsDataModel(GOALKEEPER_SAVES, "1"),
                    StatisticsDataModel(TOTAL_PASSES, "495"),
                    StatisticsDataModel(PASSES_ACCURATE, "374"),
                    StatisticsDataModel(PASSES, "76%")
                )
            ), EventStatistics(
                504, listOf(
                    StatisticsDataModel(SHOTS_ON_GOAL, "3"),
                    StatisticsDataModel(SHOTS_OFF_GOAL, "0"),
                    StatisticsDataModel(TOTAL_SHOTS, "5"),
                    StatisticsDataModel(BLOCKED_SHOTS, "2"),
                    StatisticsDataModel(SHOTS_INSIDE_BOX, "3"),
                    StatisticsDataModel(SHOTS_OUTSIDE_BOX, "2"),
                    StatisticsDataModel(FOULS, "14"),
                    StatisticsDataModel(CORNER_KICKS, "2"),
                    StatisticsDataModel(OFFSIDES, "3"),
                    StatisticsDataModel(BALL_POSSESSION, "42%"),
                    StatisticsDataModel(YELLOW_CARDS, "2"),
                    StatisticsDataModel(RED_CARDS, "0"),
                    StatisticsDataModel(GOALKEEPER_SAVES, "1"),
                    StatisticsDataModel(TOTAL_PASSES, "375"),
                    StatisticsDataModel(PASSES_ACCURATE, "272"),
                    StatisticsDataModel(PASSES, "73%")
                )
            )
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