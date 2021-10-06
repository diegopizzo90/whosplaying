package com.diegopizzo.whosplaying.ui.splashscreen.config

import com.diegopizzo.whosplaying.ui.splashscreen.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashScreenViewModelModule = module {
    viewModel {
        SplashScreenViewModel(get())
    }
}