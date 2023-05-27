package com.diegopizzo.whosplaying.ui.detailsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegopizzo.network.model.EventDataModel
import com.diegopizzo.repository.event.IEventRepository
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.detailsscreen.DetailsScreenResult.ShowProgressBar
import com.diegopizzo.whosplaying.ui.detailsscreen.DetailsScreenResult.ShowSuccessResult
import com.diegopizzo.whosplaying.ui.mainscreen.navigation.IAppNavigator
import kotlinx.coroutines.launch

class DetailsScreenViewModel(
    private val eventRepository: IEventRepository,
    private val appNavigator: IAppNavigator
) : ViewModel() {

    private val _viewStates: MutableLiveData<FixtureDetailsViewState> = MutableLiveData()
    fun viewStates(): LiveData<FixtureDetailsViewState> = _viewStates

    private var _viewState: FixtureDetailsViewState? = null
    private var viewState: FixtureDetailsViewState
        get() = _viewState
            ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            _viewState = value
            _viewStates.value = value
        }

    init {
        viewState = FixtureDetailsViewState()
    }

    fun getFixtureEventDetails(id: Long) {
        viewModelScope.launch {
            //Show loading just the first time
            if (viewState.eventDataModel.fixtureId == 0L) {
                viewState = viewState.copy(screenResult = ShowProgressBar)
            }
            eventRepository.getEvent(id).collect { fixtureEvent ->
                viewState = when {
                    fixtureEvent != null -> {
                        viewState.copy(
                            eventDataModel = fixtureEvent,
                            screenResult = ShowSuccessResult
                        )
                    }
                    else -> viewState.copy(
                        eventDataModel = EventDataModel(),
                        screenResult = ShowSuccessResult
                    )
                }
            }
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            appNavigator.navigateBack()
        }
    }
}

data class FixtureDetailsViewState(
    val eventDataModel: EventDataModel = EventDataModel(),
    val tabViewDataList: List<TabPager> = listOf(
        TabPager.MatchEvents,
        TabPager.Statistics,
        TabPager.Lineup
    ),
    val screenResult: DetailsScreenResult? = null,
)

sealed class TabPager(val id: Int, val tabNameRes: Int) {
    object MatchEvents : TabPager(0, R.string.match_events)
    object Statistics : TabPager(1, R.string.statistics)
    object Lineup : TabPager(2, R.string.lineups)
}

sealed class DetailsScreenResult {
    object ShowSuccessResult : DetailsScreenResult()
    object ShowProgressBar : DetailsScreenResult()
}