package com.diegopizzo.whosplaying.ui.detailsscreen.config

import com.diegopizzo.whosplaying.ui.detailsscreen.DetailsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsScreenViewModelModule = module {
    viewModel { DetailsScreenViewModel(get()) }
}