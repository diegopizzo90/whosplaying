package com.diegopizzo.whosplaying.ui.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegopizzo.network.interactor.league.CountryCode
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.repository.fixture.IFixtureRepository
import com.diegopizzo.repository.league.ILeagueRepository
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
) : ViewModel() {

    private val _viewStates: MutableLiveData<MainViewState> = MutableLiveData()
    fun viewStates(): LiveData<MainViewState> = _viewStates

    private val _viewEffects: SingleLiveEvent<ViewEffect> = SingleLiveEvent()
    fun viewEffects(): SingleLiveEvent<ViewEffect> = _viewEffects

    private var _viewState: MainViewState? = null
    var viewState: MainViewState
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
            indexDateSelected = datePickerList.indexCurrentDate()
        )
    }

    fun getFixturesByLeagueName(countryCode: CountryCode, localDate: LocalDate? = null) {
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

    fun onDaySelected(datePickerModel: DatePickerSliderModel) {
        viewState = viewState.copy(
            dateSelected = datePickerModel.fullDate,
            indexDateSelected = datePickerModel.index,
            fixtures = emptyList(),
            updateFixture = true
        )
    }

    fun onMenuNavigationSelected(countryCode: CountryCode) {
        viewState =
            viewState.copy(
                leagueCountrySelected = countryCode,
                updateFixture = true,
                fixtures = emptyList()
            )
    }

    fun onStopView() {
        job?.cancel()
        viewState = viewState.copy(updateFixture = true)
    }
}

internal data class MainViewState(
    val fixtures: List<FixtureDataModel> = emptyList(),
    val leagueCountrySelected: CountryCode = CountryCode.ITALY,
    val leagueSelected: LeagueName = LeagueName.SERIE_A,
    val dateSelected: LocalDate? = null,
    val updateFixture: Boolean = false,
    val datePickerSliderModel: List<DatePickerSliderModel>,
    val indexDateSelected: Int
)

internal sealed class ViewEffect {
    object ShowSuccessResult : ViewEffect()
    object ShowErrorResult : ViewEffect()
    object ShowProgressBar : ViewEffect()
}