package com.diegopizzo.whosplaying.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.StandingsDataModel
import com.diegopizzo.repository.standings.IStandingsRepository
import kotlinx.coroutines.launch

class StandingsViewModel(private val repository: IStandingsRepository) : ViewModel() {

    private val _viewStates: MutableLiveData<StandingsViewState> = MutableLiveData()
    fun viewStates(): LiveData<StandingsViewState> = _viewStates

    private var _viewState: StandingsViewState? = null
    var viewState: StandingsViewState
        get() = _viewState
            ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            _viewState = value
            _viewStates.value = value
        }

    init {
        viewState = StandingsViewState()
    }


    fun getStandings(leagueName: LeagueName) {
        viewState = viewState.copy(leagueName = leagueName, standings = emptyList(), isLoading = true)
        viewModelScope.launch {
            val list = repository.getStandingsByLeague(leagueName)
            viewState = viewState.copy(leagueName = leagueName, standings = list, isLoading = false)
        }
    }
}

data class StandingsViewState(
    val leagueName: LeagueName = LeagueName.SERIE_A,
    val standings: List<StandingsDataModel> = emptyList(),
    val isLoading: Boolean = false
)