package com.diegopizzo.network.creator

import com.diegopizzo.network.creator.event.EventModelCreator
import com.diegopizzo.network.model.*
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
        val actual = creator.toEventDataModel(model)
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
            LeagueDetails(2, "League", "www.league.it", 2021, "Round 4"),
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
                    "90′ + 2′",
                    517,
                    "S. Kiyine",
                    "D. Črnigoj",
                    EventType.GOAL,
                    EventTypeDetail.NORMAL_GOAL
                )
            )
        )
    }
}