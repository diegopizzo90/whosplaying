package com.diegopizzo.whosplaying.database.creator.league

import org.koin.dsl.module

val leagueCreatorModule = module {
    factory { LeagueCreator() }
}