package com.diegopizzo.whosplaying.ui.standings.config

import com.diegopizzo.whosplaying.ui.standings.StandingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val standingsViewModelModule = module {
    viewModel { StandingsViewModel(get()) }
}