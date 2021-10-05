package com.diegopizzo.network.service

import com.diegopizzo.network.model.*
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

    companion object {
        private val leagueInfo =
            LeagueInfo(140, "La Liga", "https://media.api-sports.io/football/leagues/140.png")
        private val leagueSuccessResponse =
            Response.success(LeagueModel(listOf(LeagueResponse(leagueInfo))))

        private val fixture = ResponseFixture(
            Fixture("UTC", "2021-10-01T18:45:00+00:00", Status("NS", null)), Teams(
                Home("Cagliari", "https://media.api-sports.io/football/teams/490.png"),
                Away("Venezia", "https://media.api-sports.io/football/teams/517.png")
            ), Goals(null, null)
        )
        private val fixtureSuccessResponse =
            Response.success(FixtureModel(listOf(fixture)))
    }
}