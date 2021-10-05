package com.diegopizzo.network.interactor.league

import org.koin.dsl.module

val leagueInteractorModule = module {
    factory<ILeagueInteractor> { LeagueInteractor(get()) }
}