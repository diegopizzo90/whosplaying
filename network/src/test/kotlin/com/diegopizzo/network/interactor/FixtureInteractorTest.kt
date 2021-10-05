package com.diegopizzo.network.interactor

import com.diegopizzo.network.creator.FixtureModelCreator
import com.diegopizzo.network.interactor.fixture.FixtureInteractor
import com.diegopizzo.network.interactor.fixture.IFixtureInteractor
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.network.service.RetrofitApi
import com.diegopizzo.network.testutil.enqueueResponse
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.threeten.bp.LocalDate
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FixtureInteractorTest {

    private lateinit var server: MockWebServer

    private lateinit var api: RetrofitApi

    private lateinit var interactor: IFixtureInteractor

    @Before
    fun setUp() {
        server = MockWebServer()

        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(RetrofitApi::class.java)
        interactor = FixtureInteractor(api, FixtureModelCreator())
    }

    @After
    fun shutDown() {
        server.shutdown()
    }

    @Test
    fun getFixture_recordedRequest_assertEqualsTrue() {
        server.enqueueResponse("fixtures_success_response.json", 200)
        runBlocking {
            val item = interactor.getFixturesByLeagueAndDate(
                "1",
                LocalDate.of(2021, 9, 30).minusDays(3),
                LocalDate.of(2021, 9, 30).plusDays(3)
            ).first()

            assertEquals(dataModel, item)
        }
    }

    companion object {
        private val dataModel = listOf(
            FixtureDataModel(
                "01/10/2021",
                "19:45",
                "",
                "Cagliari",
                "Venezia",
                "https://media.api-sports.io/football/teams/490.png",
                "https://media.api-sports.io/football/teams/517.png",
                "",
                ""
            )
        )
    }
}