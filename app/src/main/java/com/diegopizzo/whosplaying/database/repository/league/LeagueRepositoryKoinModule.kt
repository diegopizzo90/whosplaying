package com.diegopizzo.whosplaying.database.repository.league

import com.diegopizzo.network.interactor.league.LeagueName
import org.koin.dsl.module

val leagueRepositoryModule = module {
    factory<ILeagueRepository> {
        LeagueRepository(get(), get(), get(), provideLeagueList())
    }
}

fun provideLeagueList(): List<LeagueName> {
    return LeagueName.values().toList()
}