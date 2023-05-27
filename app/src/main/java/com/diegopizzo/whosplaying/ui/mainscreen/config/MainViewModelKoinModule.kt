package com.diegopizzo.whosplaying.ui.mainscreen.config

import com.diegopizzo.whosplaying.ui.mainscreen.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainViewModelModule = module {
    viewModel { MainViewModel(get()) }
}