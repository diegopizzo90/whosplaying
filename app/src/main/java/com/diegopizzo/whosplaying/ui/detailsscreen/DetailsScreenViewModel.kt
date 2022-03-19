package com.diegopizzo.whosplaying.ui.detailsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegopizzo.network.model.EventDataModel
import com.diegopizzo.repository.event.IEventRepository
import kotlinx.coroutines.launch

internal class DetailsScreenViewModel(private val eventRepository: IEventRepository) : ViewModel() {

    private val _viewStates: MutableLiveData<FixtureDetailsViewState> = MutableLiveData()
    fun viewStates(): LiveData<FixtureDetailsViewState> = _viewStates

    private var _viewState: FixtureDetailsViewState? = null
    var viewState: FixtureDetailsViewState
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
                viewState = viewState.copy(isLoading = true)
            }
            eventRepository.getEvent(id).collect { fixtureEvent ->
                viewState = when {
                    fixtureEvent != null -> {
                        viewState.copy(eventDataModel = fixtureEvent, isLoading = false)
                    }
                    else -> viewState.copy(eventDataModel = EventDataModel(), isLoading = false)
                }
            }
        }
    }
}

internal data class FixtureDetailsViewState(
    val eventDataModel: EventDataModel = EventDataModel(),
    val isLoading: Boolean = false
)