package com.diegopizzo.whosplaying.ui.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegopizzo.whosplaying.database.repository.league.ILeagueRepository
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val repository: ILeagueRepository) : ViewModel() {

    fun retrieveLeaguesInfo() {
        viewModelScope.launch {
            repository.downloadLeaguesInfo()
        }
    }
}