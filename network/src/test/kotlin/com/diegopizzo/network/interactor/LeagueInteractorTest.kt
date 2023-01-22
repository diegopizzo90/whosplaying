package com.diegopizzo.network.interactor

import com.diegopizzo.network.interactor.league.ILeagueInteractor
import com.diegopizzo.network.interactor.league.LeagueInteractor
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.interactor.league.LeagueType
import com.diegopizzo.network.model.CountryInfo
import com.diegopizzo.network.model.LeagueInfo
import com.diegopizzo.network.model.LeagueModel
import com.diegopizzo.network.model.LeagueResponse
import com.diegopizzo.network.service.RetrofitApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
        runTest {
            `when`(api.getLeagueByCountry(anyString(), anyString())).then { Response.success(null) }

            val actualValue = interactor.getLeague(LeagueName.SERIE_A)
            assertEquals(actualValue, null)
        }
    }

    @Test
    fun getLeague_successResponse_assertEqualsTrue() {
        runTest {
            `when`(api.getLeagueByCountry(anyString(), anyString())).then {
                Response.success(
                    successResponse
                )
            }

            val actualValue = interactor.getLeague(LeagueName.SERIE_A)
            assertEquals(actualValue, LeagueResponse(leagueInfo, countryInfo))
        }
    }

    companion object {
        private val leagueInfo = LeagueInfo(1, "name", LeagueType.LEAGUE.type,"logo.it")
        private val countryInfo = CountryInfo("name", "code")
        private val leagueInfo2 = LeagueInfo(2, "name2", LeagueType.CUP.type, "logo2.it")
        private val countryInfo2 = CountryInfo("name2", "code2")
        private val successResponse = LeagueModel(
            listOf(
                LeagueResponse(leagueInfo, countryInfo),
                LeagueResponse(leagueInfo2, countryInfo2)
            )
        )
    }
}