package com.diegopizzo.whosplaying.ui.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.whosplaying.repository.fixture.IFixtureRepository
import com.diegopizzo.whosplaying.repository.league.ILeagueRepository
import com.diegopizzo.whosplaying.ui.base.SingleLiveEvent
import com.diegopizzo.whosplaying.ui.mainscreen.ViewEffect.*
import kotlinx.coroutines.flow.collect
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

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

    init {
        viewState = MainViewState()
    }

    suspend fun getFixturesByLeagueName(leagueName: LeagueName) {
        _viewEffects.value = ShowProgressBar
        val leagueId = leagueRepository.getLeagueId(leagueName)
        if (leagueId != null) {
            val range = getLocalDateRange()
            fixtureRepository.getFixtures(leagueId.toString(), range.first, range.second)
                .collect {
                    when {
                        it == null -> showError()
                        it.isEmpty() -> showError()
                        else -> {
                            _viewEffects.value = ShowSuccessResult
                            viewState = viewState.copy(fixtures = it)
                        }
                    }
                }
        } else showError()
    }

    fun onFixtureSelected(fixtureId: Long) {
        _viewEffects.value = ShowFixtureDetails(fixtureId)
    }

    private fun showError() {
        _viewEffects.value = ShowErrorResult
    }

    private fun getLocalDateRange(): Pair<LocalDate, LocalDate> {
        val now = ZonedDateTime.now(ZoneId.systemDefault())
        return Pair(now.minusDays(4).toLocalDate(), now.plusDays(4).toLocalDate())
    }
}

internal data class MainViewState(val fixtures: List<FixtureDataModel> = emptyList())

internal sealed class ViewEffect {
    object ShowSuccessResult : ViewEffect()
    object ShowErrorResult : ViewEffect()
    object ShowProgressBar : ViewEffect()
    data class ShowFixtureDetails(val id: Long) : ViewEffect()
}