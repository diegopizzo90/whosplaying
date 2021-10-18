package com.diegopizzo.whosplaying.repository.league

import com.diegopizzo.network.interactor.league.LeagueName
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val leagueRepositoryModule = module {
    factory<ILeagueRepository> {
        LeagueRepository(get(), get(), get(), provideLeagueList(), Dispatchers.IO)
    }
}

fun provideLeagueList(): List<LeagueName> {
    return LeagueName.values().toList()
}