package com.diegopizzo.whosplaying.ui.mainscreen.config

import com.diegopizzo.whosplaying.ui.mainscreen.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
}