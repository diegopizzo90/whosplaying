package com.diegopizzo.whosplaying.ui.mainscreen

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
import com.diegopizzo.whosplaying.ui.mainscreen.ViewEffect.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

internal class MainViewModel(
    private val leagueRepository: ILeagueRepository,
    private val fixtureRepository: IFixtureRepository,
) : ViewModel(), IMainViewModel {

    private val _viewStates: MutableLiveData<MainViewState> = MutableLiveData()
    override fun viewStates(): LiveData<MainViewState> = _viewStates

    private val _viewEffects: SingleLiveEvent<ViewEffect> = SingleLiveEvent()
    override fun viewEffects(): SingleLiveEvent<ViewEffect> = _viewEffects

    private var _viewState: MainViewState? = null
    override var viewState: MainViewState
        get() = _viewState
            ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            _viewState = value
            _viewStates.value = value
        }

    private var job: Job? = null

    init {
        val datePickerList = createDatePickerSliderModel()
        viewState = MainViewState(
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

    private fun getBottomNavigationList(): List<BottomNavItem> {
        return listOf(
            BottomNavItem(
                R.drawable.ic_serie_a,
                R.string.serie_a,
                SERIE_A,
            ),
            BottomNavItem(
                R.drawable.ic_premier_league,
                R.string.premier_league,
                PREMIER_LEAGUE,
            ),
            BottomNavItem(
                R.drawable.ic_la_liga,
                R.string.la_liga,
                LA_LIGA,
            ),
            BottomNavItem(
                R.drawable.ic_bundesliga,
                R.string.bundesliga,
                BUNDESLIGA,
            ),
            BottomNavItem(
                R.drawable.ic_ligue_1,
                R.string.ligue_1,
                LIGUE_1,
            ),
        )
    }
}

data class MainViewState(
    val fixtures: List<FixtureDataModel> = emptyList(),
    val leagueCountrySelected: CountryCode = CountryCode.ITALY,
    val leagueSelected: LeagueName = SERIE_A,
    val dateSelected: LocalDate? = null,
    val updateFixture: Boolean = false,
    val datePickerSliderModel: List<DatePickerSliderModel>,
    val indexDateSelected: Int,
    val bottomNavItems: List<BottomNavItem>
)

data class BottomNavItem(
    val itemIcon: Int,
    val itemName: Int,
    val id: LeagueName,
)

sealed class ViewEffect {
    object ShowSuccessResult : ViewEffect()
    object ShowErrorResult : ViewEffect()
    object ShowProgressBar : ViewEffect()
}

interface IMainViewModel {
    fun viewStates(): LiveData<MainViewState>
    fun viewEffects(): SingleLiveEvent<ViewEffect>
    var viewState: MainViewState
    fun getFixturesByLeagueName(countryCode: CountryCode, localDate: LocalDate? = null)
    fun onDaySelected(datePickerModel: DatePickerSliderModel)
    fun onBottomMenuNavigationSelected(countryCode: CountryCode)
    fun onStopView()
}