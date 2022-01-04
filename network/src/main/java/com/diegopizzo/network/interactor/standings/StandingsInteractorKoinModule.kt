package com.diegopizzo.network.interactor.standings

import org.koin.dsl.module

val standingsInteractorModule = module {
    factory<IStandingsInteractor> { StandingsInteractor(get(), get()) }
}