package com.diegopizzo.whosplaying.ui.mainscreen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegopizzo.network.interactor.league.CountryCode
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.interactor.league.LeagueName.*
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.repository.fixture.IFixtureRepository
import com.diegopizzo.repository.league.ILeagueRepository
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.base.SingleLiveEvent
import com.diegopizzo.whosplaying.ui.component.datepickerslider.DatePickerSliderModel
import com.diegopizzo.whosplaying.ui.component.datepickerslider.createDatePickerSliderModel
import com.diegopizzo.whosplaying.ui.component.datepickerslider.indexCurrentDate
import com.diegopizzo.whosplaying.ui.mainscreen.BottomNavScreen.*
import com.diegopizzo.whosplaying.ui.mainscreen.ViewEffect.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class HomeViewModel(
    private val leagueRepository: ILeagueRepository,
    private val fixtureRepository: IFixtureRepository,
) : ViewModel(), IHomeViewModel {

    private val _viewStates: MutableLiveData<HomeViewState> = MutableLiveData()
    override fun viewStates(): LiveData<HomeViewState> = _viewStates

    private val _viewEffects: SingleLiveEvent<ViewEffect> = SingleLiveEvent()
    override fun viewEffects(): SingleLiveEvent<ViewEffect> = _viewEffects

    private var _viewState: HomeViewState? = null
    override var viewState: HomeViewState
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
            bottomNavItems = getBottomNavigationList()
        )
    }

    override fun getFixturesByLeagueName(countryCode: CountryCode, localDate: LocalDate?) {
        if (localDate == null) return
        if (viewState.fixtures.isEmpty()) _viewEffects.value = ShowProgressBar

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
        _viewEffects.value = ShowSuccessResult
        viewState = viewState.copy(fixtures = list, updateFixture = false)
    }

    private fun onError() {
        viewState = viewState.copy(fixtures = emptyList(), updateFixture = false)
        _viewEffects.value = ShowErrorResult
    }

    override fun onDaySelected(datePickerModel: DatePickerSliderModel) {
        viewState = viewState.copy(
            dateSelected = datePickerModel.fullDate,
            indexDateSelected = datePickerModel.index,
            fixtures = emptyList(),
            updateFixture = true
        )
    }

    override fun onBottomMenuNavigationSelected(countryCode: CountryCode) {
        viewState =
            viewState.copy(
                leagueCountrySelected = countryCode,
                updateFixture = true,
                fixtures = emptyList()
            )
    }

    override fun onStopView() {
        job?.cancel()
        viewState = viewState.copy(updateFixture = true)
    }

    private fun getBottomNavigationList(): List<BottomNavScreen> {
        return listOf(SerieA, PremierLeague, LaLiga, Bundesliga, Ligue1)
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
    val bottomNavItems: List<BottomNavScreen>
)

sealed class BottomNavScreen(
    @StringRes val itemName: Int,
    val id: LeagueName,
    @DrawableRes val itemIcon: Int
) {
    object SerieA : BottomNavScreen(
        itemName = R.string.serie_a,
        id = SERIE_A,
        itemIcon = R.drawable.ic_serie_a,
    )

    object PremierLeague : BottomNavScreen(
        itemName = R.string.premier_league,
        id = PREMIER_LEAGUE,
        itemIcon = R.drawable.ic_premier_league,
    )

    object LaLiga : BottomNavScreen(
        itemName = R.string.la_liga,
        id = LA_LIGA,
        itemIcon = R.drawable.ic_la_liga,
    )

    object Bundesliga : BottomNavScreen(
        itemName = R.string.bundesliga,
        id = BUNDESLIGA,
        itemIcon = R.drawable.ic_bundesliga,
    )

    object Ligue1 : BottomNavScreen(
        itemName = R.string.ligue_1,
        id = LIGUE_1,
        itemIcon = R.drawable.ic_ligue_1,
    )
}

sealed class ViewEffect {
    object ShowSuccessResult : ViewEffect()
    object ShowErrorResult : ViewEffect()
    object ShowProgressBar : ViewEffect()
}

interface IHomeViewModel {
    fun viewStates(): LiveData<HomeViewState>
    fun viewEffects(): SingleLiveEvent<ViewEffect>
    var viewState: HomeViewState
    fun getFixturesByLeagueName(countryCode: CountryCode, localDate: LocalDate? = null)
    fun onDaySelected(datePickerModel: DatePickerSliderModel)
    fun onBottomMenuNavigationSelected(countryCode: CountryCode)
    fun onStopView()
}