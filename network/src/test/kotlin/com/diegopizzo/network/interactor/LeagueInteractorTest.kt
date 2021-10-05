package com.diegopizzo.network.interactor

import com.diegopizzo.network.interactor.league.ILeagueInteractor
import com.diegopizzo.network.interactor.league.LeagueInteractor
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.LeagueInfo
import com.diegopizzo.network.model.LeagueModel
import com.diegopizzo.network.model.LeagueResponse
import com.diegopizzo.network.service.RetrofitApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LeagueInteractorTest {

    @Mock
    private lateinit var api: RetrofitApi

    private lateinit var interactor: ILeagueInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        interactor = LeagueInteractor(api)
    }

    @Test
    fun getLeague_failedResponse_assertEqualsTrue() {
        runBlockingTest {
            `when`(api.getLeagueByCountry(anyString(), anyString())).then { Response.success(null) }

            val actualValue = interactor.getLeague(LeagueName.SERIE_A)
            assertEquals(actualValue, null)
        }
    }

    @Test
    fun getLeague_successResponse_assertEqualsTrue() {
        runBlockingTest {
            `when`(api.getLeagueByCountry(anyString(), anyString())).then { Response.success(successResponse) }

            val actualValue = interactor.getLeague(LeagueName.SERIE_A)
            assertEquals(actualValue, leagueInfo)
        }
    }

    companion object {
        private val leagueInfo = LeagueInfo(1, "name", "logo.it")
        private val leagueInfo2 = LeagueInfo(2, "name2", "logo2.it")
        private val successResponse = LeagueModel(
            listOf(LeagueResponse(leagueInfo), LeagueResponse(leagueInfo2))
        )
    }
}