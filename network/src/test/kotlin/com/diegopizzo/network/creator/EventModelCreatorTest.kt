package com.diegopizzo.network.creator

import com.diegopizzo.network.creator.event.EventModelCreator
import com.diegopizzo.network.model.*
import com.diegopizzo.network.model.EventStatistics.StatisticsType.*
import com.diegopizzo.network.model.LineupsDataModel.PlayerDataModel
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
        val actual = creator.toEventDataModel(model, statisticsModel, lineupsModel, null)
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

        private val lineupsModel = LineupsModel(
            listOf(
                Lineup(
                    TeamLineup(497, "AS Roma"),
                    CoachLineup(2462, "José Mourinho"),
                    "3-4-1-2",
                    startXI = listOf(
                        StartEleven(PlayerLineup(2674, "Rui Patrício", "1", "G", "1:1")),
                        StartEleven(PlayerLineup(892, "C. Smalling", "6", "D", "2:3")),
                        StartEleven(PlayerLineup(770, "R. Karsdorp", "2", "D", "2:2")),
                        StartEleven(PlayerLineup(30924, "M. Kumbulla", "24", "D", "2:1")),
                        StartEleven(PlayerLineup(51572, "M. Viña", "5", "M", "3:4")),
                        StartEleven(PlayerLineup(2375, "Sérgio Oliveira", "27", "M", "3:3")),
                        StartEleven(PlayerLineup(778, "B. Cristante", "4", "M", "3:2")),
                        StartEleven(PlayerLineup(1456, "A. Maitland-Niles", "15", "M", "3:1")),
                        StartEleven(PlayerLineup(782, "L. Pellegrini", "7", "F", "4:1")),
                        StartEleven(PlayerLineup(19194, "T. Abraham", "9", "F", "5:2")),
                        StartEleven(PlayerLineup(342038, "F. Afena-Gyan", "64", "F", "5:1"))
                    ),
                    substitutes = listOf(
                        Substitutes(PlayerLineup(30409, "J. Veretout", "17", "M", null)),
                        Substitutes(PlayerLineup(203474, "N. Zalewski", "59", "F", null)),
                        Substitutes(PlayerLineup(342035, "C. Volpato", "62", "M", null)),
                        Substitutes(PlayerLineup(286475, "E. Bove", "52", "M", null)),
                        Substitutes(PlayerLineup(763, "Daniel Fuzato", "87", "G", null)),
                        Substitutes(PlayerLineup(342653, "F. Missori", "58", "D", null)),
                        Substitutes(PlayerLineup(342071, "D. Mastrantonio", "67", "G", null)),
                        Substitutes(PlayerLineup(324, "A. Diawara", "42", "M", null)),
                        Substitutes(PlayerLineup(343385, "D. Keramitsis", "75", "D", null)),
                        Substitutes(PlayerLineup(158059, "E. Darboe", "55", "M", null))
                    )
                ), Lineup(
                    TeamLineup(504, "Verona"),
                    CoachLineup(2432, "I. Tudor"),
                    "3-4-2-1",
                    startXI = emptyList(),
                    substitutes = emptyList()
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

        private val lineupsDataModel = LineupsDataModel(
            homeTeamLineup = LineupsDataModel.TeamLineup(
                497,
                "AS Roma",
                "José Mourinho",
                "3-4-1-2",
                listOf(
                    PlayerDataModel(2674, "Rui Patrício", "1", "G"),
                    PlayerDataModel(892, "C. Smalling", "6", "D"),
                    PlayerDataModel(770, "R. Karsdorp", "2", "D"),
                    PlayerDataModel(30924, "M. Kumbulla", "24", "D"),
                    PlayerDataModel(51572, "M. Viña", "5", "M"),
                    PlayerDataModel(2375, "Sérgio Oliveira", "27", "M"),
                    PlayerDataModel(778, "B. Cristante", "4", "M"),
                    PlayerDataModel(1456, "A. Maitland-Niles", "15", "M"),
                    PlayerDataModel(782, "L. Pellegrini", "7", "F"),
                    PlayerDataModel(19194, "T. Abraham", "9", "F"),
                    PlayerDataModel(342038, "F. Afena-Gyan", "64", "F")
                ),
                listOf(
                    PlayerDataModel(30409, "J. Veretout", "17", "M"),
                    PlayerDataModel(203474, "N. Zalewski", "59", "F"),
                    PlayerDataModel(342035, "C. Volpato", "62", "M"),
                    PlayerDataModel(286475, "E. Bove", "52", "M"),
                    PlayerDataModel(763, "Daniel Fuzato", "87", "G"),
                    PlayerDataModel(342653, "F. Missori", "58", "D"),
                    PlayerDataModel(342071, "D. Mastrantonio", "67", "G"),
                    PlayerDataModel(324, "A. Diawara", "42", "M"),
                    PlayerDataModel(343385, "D. Keramitsis", "75", "D"),
                    PlayerDataModel(158059, "E. Darboe", "55", "M")
                )
            ),
            awayTeamLineup = LineupsDataModel.TeamLineup(
                504,
                "Verona",
                "I. Tudor",
                "3-4-2-1",
                listOf(),
                listOf()
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
            ), statisticsDataModel, lineupsDataModel
        )
    }
}