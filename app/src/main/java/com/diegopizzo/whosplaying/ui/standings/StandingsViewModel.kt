package com.diegopizzo.whosplaying.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.StandingsDataModel
import com.diegopizzo.repository.standings.IStandingsRepository

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
        viewState = StandingsViewState(LeagueName.SERIE_A)
    }

    suspend fun getStandings() {
        viewState = viewState.copy(standings = emptyList(), isLoading = true)
        val list = repository.getStandingsByLeague(viewState.leagueName)
        viewState = viewState.copy(standings = list, isLoading = false)
    }

    fun onMenuItemSelected(name: LeagueName) {
        viewState = viewState.copy(leagueName = name)
    }
}

data class StandingsViewState(
    val leagueName: LeagueName,
    val standings: List<StandingsDataModel> = emptyList(),
    val isLoading: Boolean = false
)