package com.diegopizzo.network.service

import com.diegopizzo.network.interactor.league.LeagueType
import com.diegopizzo.network.model.*
import com.diegopizzo.network.model.EventType.*
import com.diegopizzo.network.model.EventTypeDetail.*
import com.diegopizzo.network.testutil.enqueueResponse
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RetrofitApiTest {

    private val server = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(server.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()
        .create(RetrofitApi::class.java)

    @After
    fun destroy() {
        server.shutdown()
    }

    @Test
    fun getLeagueByCountry_recordedRequest_assertEqualsTrue() {
        server.enqueueResponse("league_success_response.json", 200)

        runBlocking {
            api.getLeagueByCountry("", "")

            val request = server.takeRequest()
            assertEquals(request.path?.contains("/leagues"), true)
        }
    }

    @Test
    fun getLeagueByCountry_successResponse_assertEqualsTrue() {
        server.enqueueResponse("league_success_response.json", 200)

        runBlocking {
            val actualValue = api.getLeagueByCountry("", "")
            assertEquals(actualValue.body(), leagueSuccessResponse.body())
        }
    }

    @Test
    fun getLeagueByCountry_errorResponse_assertEqualsTrue() {
        server.enqueue(MockResponse().setResponseCode(500))

        runBlocking {
            val actualValue = api.getLeagueByCountry("", "")
            assertEquals(actualValue.body(), null)
        }
    }

    @Test
    fun getFixturesByLeagueIdAndByDate_recordedRequest_assertEqualsTrue() {
        server.enqueueResponse("fixtures_success_response.json", 200)

        runBlocking {
            api.getFixturesByLeagueIdAndByDate("", "", "", "")

            val request = server.takeRequest()
            assertEquals(request.path?.contains("/fixtures"), true)
        }
    }

    @Test
    fun getFixturesByLeagueIdAndByDate_successResponse_assertEqualsTrue() {
        server.enqueueResponse("fixtures_success_response.json", 200)

        runBlocking {
            val actualValue = api.getFixturesByLeagueIdAndByDate("", "", "", "")

            assertEquals(actualValue.body(), fixtureSuccessResponse.body())
        }
    }

    @Test
    fun getFixturesByLeagueIdAndByDate_errorResponse_assertEqualsTrue() {
        server.enqueue(MockResponse().setResponseCode(500))

        runBlocking {
            val actualValue = api.getFixturesByLeagueIdAndByDate("", "", "", "")

            assertEquals(actualValue.body(), null)
        }
    }

    @Test
    fun getEventByFixtureId_successResponse_assertEqualsTrue() {
        server.enqueueResponse("event_success_response.json", 200)

        runBlocking {
            val actualValue = api.getEventByFixtureId(1L)

            assertEquals(actualValue.body(), eventModel)
        }
    }

    @Test
    fun getStanding_successResponse_assertEqualsTrue() {
        server.enqueueResponse("standings_success_response.json", 200)

        runBlocking {
            val actualValue = api.getStandings("", "")

            assertEquals(actualValue.body(), standings)
        }
    }

    @Test
    fun getStatistics_successResponse_assertEqualsTrue() {
        server.enqueueResponse("statistics_success_response.json", 200)

        runBlocking {
            val actualValue = api.getStatistics(1L)

            assertEquals(actualValue.body(), statistics)
        }
    }

    @Test
    fun getLineups_successResponse_assertEqualsTrue() {
        server.enqueueResponse("lineup_success_response.json", 200)

        runBlocking {
            val actualValue = api.getLineups(1L)

            assertEquals(actualValue.body(), lineupsModel)
        }
    }

    @Test
    fun getHeadToHead_successResponse_assertEqualsTrue() {
        server.enqueueResponse("head_to_head_success_response.json", 200)

        runBlocking {
            val actualValue = api.getHeadToHead("499-495")

            assertEquals(actualValue.body(), headToHeadModel)
        }
    }

    companion object {
        private val leagueInfo =
            LeagueInfo(
                140,
                "La Liga",
                LeagueType.LEAGUE.type,
                "https://media.api-sports.io/football/leagues/140.png"
            )
        private val countryInfo = CountryInfo(name = "Spain", code = "ES")
        private val leagueSuccessResponse =
            Response.success(LeagueModel(listOf(LeagueResponse(leagueInfo, countryInfo))))

        private val fixtures = listOf(
            ResponseFixture(
                Fixture(
                    731646,
                    "UTC",
                    "2021-10-01T18:45:00+00:00",
                    Status("Not Started", "NS", null)
                ), Teams(
                    Home(490, "Cagliari", "https://media.api-sports.io/football/teams/490.png"),
                    Away(517, "Venezia", "https://media.api-sports.io/football/teams/517.png")
                ), Goals(null, null)
            ),
            ResponseFixture(
                Fixture(
                    731646,
                    "UTC",
                    "2021-09-01T16:45:00+00:00",
                    Status("Not Started", "NS", null)
                ), Teams(
                    Home(490, "Cagliari", "https://media.api-sports.io/football/teams/490.png"),
                    Away(517, "Venezia", "https://media.api-sports.io/football/teams/517.png")
                ), Goals(null, null)
            ),
            ResponseFixture(
                Fixture(
                    731646,
                    "UTC",
                    "2021-10-01T17:45:00+00:00",
                    Status("Not Started", "NS", null)
                ), Teams(
                    Home(490, "Cagliari", "https://media.api-sports.io/football/teams/490.png"),
                    Away(517, "Venezia", "https://media.api-sports.io/football/teams/517.png")
                ), Goals(null, null)
            )
        )
        private val fixtureSuccessResponse =
            Response.success(FixtureModel(fixtures))


        private val eventModel = EventModel(
            listOf(
                ResponseEvents(
                    Fixture(
                        731646,
                        "UTC",
                        "2021-10-01T18:45:00+00:00",
                        Status("Match Finished", "FT", 90)
                    ),
                    LeagueDetails(
                        135,
                        "Serie A",
                        "https://media.api-sports.io/football/leagues/135.png",
                        2021, "Regular Season - 7"
                    ),
                    Teams(
                        Home(490, "Cagliari", "https://media.api-sports.io/football/teams/490.png"),
                        Away(517, "Venezia", "https://media.api-sports.io/football/teams/517.png")
                    ),
                    Goals(1, 1),
                    listOf(
                        Event(
                            Time(19, null),
                            TeamEvent(
                                490,
                                "Cagliari",
                                "https://media.api-sports.io/football/teams/490.png"
                            ),
                            Player(213, "K. Baldé"),
                            Assist(854, "M. Cáceres"), GOAL, NORMAL_GOAL
                        ),
                        Event(
                            Time(22, null),
                            TeamEvent(
                                490,
                                "Cagliari",
                                "https://media.api-sports.io/football/teams/490.png"
                            ),
                            Player(2614, "Nahitan Nández"),
                            Assist(null, null), CARD, YELLOW_CARD
                        ),
                        Event(
                            Time(46, null),
                            TeamEvent(
                                517,
                                "Venezia",
                                "https://media.api-sports.io/football/teams/517.png"
                            ),
                            Player(30752, "S. Kiyine"),
                            Assist(48406, "D. Črnigoj"), SUBSTITUTION, SUBSTITUTION_1
                        )
                    )
                )
            )
        )

        private val standings = StandingsModel(
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

        private val statistics = StatisticsModel(
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

        private val headToHeadModel = HeadToHeadModel(
            listOf(
                ResponseHeadToHead(
                    FixtureH2H(8698, "UTC", "2018-04-29T13:00:00+00:00"),
                    Teams(
                        Home(499, "Atalanta", "https://media.api-sports.io/football/teams/499.png"),
                        Away(495, "Genoa", "https://media.api-sports.io/football/teams/495.png")
                    ),
                    Goals(3, 1)
                ),
                ResponseHeadToHead(
                    FixtureH2H(21564, "UTC", "2016-10-30T11:30:00+00:00"),
                    Teams(
                        Home(499, "Atalanta", "https://media.api-sports.io/football/teams/499.png"),
                        Away(495, "Genoa", "https://media.api-sports.io/football/teams/495.png")
                    ),
                    Goals(3, 0)
                )
            )
        )
    }
}