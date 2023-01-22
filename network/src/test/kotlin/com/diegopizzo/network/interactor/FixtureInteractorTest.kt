package com.diegopizzo.network.interactor

import com.diegopizzo.network.cache.fixture.IFixtureInteractorCache
import com.diegopizzo.network.creator.fixture.FixtureModelCreator
import com.diegopizzo.network.interactor.fixture.FixtureInteractor
import com.diegopizzo.network.interactor.fixture.IFixtureInteractor
import com.diegopizzo.network.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.MockitoAnnotations
import org.threeten.bp.LocalDate
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FixtureInteractorTest {

    @Mock
    private lateinit var cache: IFixtureInteractorCache

    private lateinit var interactor: IFixtureInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        interactor = FixtureInteractor(cache, FixtureModelCreator())
    }

    @Test
    fun getFixtureByLeagueIdAndByDate_firstItem_assertEqualsTrue() {
        runBlocking {
            `when`(
                cache.getFixturesByLeagueIdAndByDate(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString()
                )
            ).thenReturn(fixtureSuccessResponse)

            val fixtureItem = interactor.getFixturesByLeagueAndDate(
                listOf("1"),
                LocalDate.of(2021, 9, 30).minusDays(3),
                LocalDate.of(2021, 9, 30).plusDays(3)
            ).first()

            assertEquals(dataModel, fixtureItem)
        }
    }

    companion object {
        private val fixtures = listOf(
            ResponseFixture(
                Fixture(1, "UTC", "2021-09-01T18:45:00+00:00", Status("Not Started","NS", null)), Teams(
                    Home(1, "Cagliari", "https://media.api-sports.io/football/teams/490.png"),
                    Away(1, "Venezia", "https://media.api-sports.io/football/teams/517.png")
                ), Goals(null, null)
            ),
            ResponseFixture(
                Fixture(1, "UTC", "2021-10-01T18:45:00+00:00", Status("Not Started","NS", null)), Teams(
                    Home(2, "Cagliari", "https://media.api-sports.io/football/teams/490.png"),
                    Away(2, "Venezia", "https://media.api-sports.io/football/teams/517.png")
                ), Goals(null, null)
            ),
            ResponseFixture(
                Fixture(1, "UTC", "2021-10-01T17:45:00+00:00", Status("Not Started","NS", null)), Teams(
                    Home(2, "Cagliari", "https://media.api-sports.io/football/teams/490.png"),
                    Away(2, "Venezia", "https://media.api-sports.io/football/teams/517.png")
                ), Goals(null, null)
            )
        )
        private val fixtureSuccessResponse =
            Response.success(FixtureModel(fixtures))

        private val dataModel = listOf(
            FixtureDataModel(
                1,
                "2021-09-01T18:45:00+00:00",
                "NS",
                null,
                "Cagliari",
                "Venezia",
                "https://media.api-sports.io/football/teams/490.png",
                "https://media.api-sports.io/football/teams/517.png",
                "",
                ""
            ),
            FixtureDataModel(
                1,
                "2021-10-01T17:45:00+00:00",
                "NS",
                null,
                "Cagliari",
                "Venezia",
                "https://media.api-sports.io/football/teams/490.png",
                "https://media.api-sports.io/football/teams/517.png",
                "",
                ""
            ),
            FixtureDataModel(
                1,
                "2021-10-01T18:45:00+00:00",
                "NS",
                null,
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