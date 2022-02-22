package com.diegopizzo.network.service

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
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
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
            api.getLeagueByCountry(anyString(), anyString())

            val request = server.takeRequest()
            assertEquals(request.path?.contains("/leagues"), true)
        }
    }

    @Test
    fun getLeagueByCountry_successResponse_assertEqualsTrue() {
        server.enqueueResponse("league_success_response.json", 200)

        runBlocking {
            val actualValue = api.getLeagueByCountry(anyString(), anyString())
            assertEquals(actualValue.body(), leagueSuccessResponse.body())
        }
    }

    @Test
    fun getLeagueByCountry_errorResponse_assertEqualsTrue() {
        server.enqueue(MockResponse().setResponseCode(500))

        runBlocking {
            val actualValue = api.getLeagueByCountry(anyString(), anyString())
            assertEquals(actualValue.body(), null)
        }
    }

    @Test
    fun getFixturesByLeagueIdAndByDate_recordedRequest_assertEqualsTrue() {
        server.enqueueResponse("fixtures_success_response.json", 200)

        runBlocking {
            api.getFixturesByLeagueIdAndByDate(anyString(), anyString(), anyString(), anyString())

            val request = server.takeRequest()
            assertEquals(request.path?.contains("/fixtures"), true)
        }
    }

    @Test
    fun getFixturesByLeagueIdAndByDate_successResponse_assertEqualsTrue() {
        server.enqueueResponse("fixtures_success_response.json", 200)

        runBlocking {
            val actualValue = api.getFixturesByLeagueIdAndByDate(
                anyString(),
                anyString(),
                anyString(),
                anyString()
            )

            assertEquals(actualValue.body(), fixtureSuccessResponse.body())
        }
    }

    @Test
    fun getFixturesByLeagueIdAndByDate_errorResponse_assertEqualsTrue() {
        server.enqueue(MockResponse().setResponseCode(500))

        runBlocking {
            val actualValue = api.getFixturesByLeagueIdAndByDate(
                anyString(),
                anyString(),
                anyString(),
                anyString()
            )

            assertEquals(actualValue.body(), null)
        }
    }

    @Test
    fun getEventByFixtureId_successResponse_assertEqualsTrue() {
        server.enqueueResponse("event_success_response.json", 200)

        runBlocking {
            val actualValue = api.getEventByFixtureId(anyLong())

            assertEquals(actualValue.body(), eventModel)
        }
    }

    @Test
    fun getStanding_successResponse_assertEqualsTrue() {
        server.enqueueResponse("standings_success_response.json", 200)

        runBlocking {
            val actualValue = api.getStandings(anyString(), anyString())

            assertEquals(actualValue.body(), standings)
        }
    }

    @Test
    fun getStatistics_successResponse_assertEqualsTrue() {
        server.enqueueResponse("statistics_success_response.json", 200)

        runBlocking {
            val actualValue = api.getStatistics(anyLong())

            assertEquals(actualValue.body(), statistics)
        }
    }

    companion object {
        private val leagueInfo =
            LeagueInfo(140, "La Liga", "https://media.api-sports.io/football/leagues/140.png")
        private val leagueSuccessResponse =
            Response.success(LeagueModel(listOf(LeagueResponse(leagueInfo))))

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
    }
}