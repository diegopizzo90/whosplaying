package com.diegopizzo.whosplaying.database.repository.league

import org.koin.dsl.module

val leagueRepositoryModule = module {
    factory<ILeagueRepository> {
        LeagueRepository(get(), get(), get())
    }
}