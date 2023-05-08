package com.diegopizzo.whosplaying.ui.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegopizzo.network.interactor.league.CountryCode
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.interactor.league.LeagueName.SERIE_A
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.repository.fixture.IFixtureRepository
import com.diegopizzo.repository.league.ILeagueRepository
import com.diegopizzo.whosplaying.ui.component.datepickerslider.DatePickerSliderModel
import com.diegopizzo.whosplaying.ui.component.datepickerslider.createDatePickerSliderModel
import com.diegopizzo.whosplaying.ui.component.datepickerslider.currentDateItem
import com.diegopizzo.whosplaying.ui.component.datepickerslider.indexCurrentDate
import com.diegopizzo.whosplaying.ui.mainscreen.ScreenResult.*
import com.diegopizzo.whosplaying.ui.mainscreen.navigation.Destination.DetailsScreen
import com.diegopizzo.whosplaying.ui.mainscreen.navigation.Destination.StandingsScreen
import com.diegopizzo.whosplaying.ui.mainscreen.navigation.IAppNavigator
import com.diegopizzo.whosplaying.ui.mainscreen.view.bottomnavigation.BottomNavScreen
import com.diegopizzo.whosplaying.ui.mainscreen.view.bottomnavigation.BottomNavScreen.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class HomeViewModel(
    private val leagueRepository: ILeagueRepository,
    private val fixtureRepository: IFixtureRepository,
    private val appNavigator: IAppNavigator,
) : ViewModel() {

    private val _viewStates: MutableLiveData<HomeViewState> = MutableLiveData()
    fun viewStates(): LiveData<HomeViewState> = _viewStates

    private var _viewState: HomeViewState? = null
    var viewState: HomeViewState
        get() = _viewState
            ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            _viewState = value
            _viewStates.value = value
        }

    private var job: Job? = null

    init {
        val datePickerList = createDatePickerSliderModel()
        viewState = HomeViewState(
            datePickerSliderModel = datePickerList,
            indexDateSelected = datePickerList.indexCurrentDate(),
            bottomNavItems = getBottomNavigationList(),
            dateSelected = datePickerList.currentDateItem().fullDate,
            updateFixture = true,
        )
    }

    fun getFixturesByLeagueName(countryCode: CountryCode, localDate: LocalDate?) {
        if (localDate == null) return
        if (viewState.updateFixture) {
            viewState = viewState.copy(screenResult = ShowProgressBar)
        }

        job?.cancel() //Cancel previous requests
        job = viewModelScope.launch {
            val leagueIds = leagueRepository.getLeagueIdsByCountry(countryCode)
            if (leagueIds != null) {
                fixtureRepository.getFixtures(leagueIds, localDate, localDate).collect {
                    when {
                        it == null || it.isEmpty() -> onError()
                        else -> onSuccess(it)
                    }
                }
            } else onError()
        }
    }

    private fun onSuccess(list: List<FixtureDataModel>) {
        viewState = viewState.copy(
            fixtures = list,
            updateFixture = false,
            screenResult = ShowSuccessResult,
        )
    }

    private fun onError() {
        viewState = viewState.copy(
            fixtures = emptyList(),
            updateFixture = false,
            screenResult = ShowErrorResult
        )
    }

    fun onDaySelected(datePickerModel: DatePickerSliderModel) {
        viewState = viewState.copy(
            dateSelected = datePickerModel.fullDate,
            indexDateSelected = datePickerModel.index,
            fixtures = emptyList(),
            updateFixture = true
        )
    }

    fun onBottomMenuNavigationSelected(leagueName: LeagueName) {
        if (viewState.leagueSelected == leagueName) return
        viewState = viewState.copy(
            leagueCountrySelected = leagueName.alpha2Code,
            leagueSelected = leagueName,
            updateFixture = true,
            fixtures = emptyList()
        )
    }

    private fun getBottomNavigationList(): List<BottomNavScreen> {
        return listOf(SerieA, PremierLeague, LaLiga, Bundesliga, Ligue1)
    }

    fun onStandingsClicked(leagueSelected: LeagueName) {
        viewModelScope.launch {
            appNavigator.navigateTo(
                route = StandingsScreen(leagueSelected.toString()),
            )
        }
    }

    fun onFixtureClicked(fixtureId: Long) {
        viewModelScope.launch {
            appNavigator.navigateTo(
                route = DetailsScreen(fixtureId.toString()),
            )
        }
    }
}

data class HomeViewState(
    val fixtures: List<FixtureDataModel> = emptyList(),
    val leagueCountrySelected: CountryCode = CountryCode.ITALY,
    val leagueSelected: LeagueName = SERIE_A,
    val dateSelected: LocalDate? = null,
    val updateFixture: Boolean = false,
    val datePickerSliderModel: List<DatePickerSliderModel>,
    val indexDateSelected: Int,
    val bottomNavItems: List<BottomNavScreen>,
    val screenResult: ScreenResult? = null,
)

sealed class ScreenResult {
    object ShowSuccessResult : ScreenResult()
    object ShowErrorResult : ScreenResult()
    object ShowProgressBar : ScreenResult()
}