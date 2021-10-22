package com.diegopizzo.whosplaying.database.creator.league

import com.diegopizzo.database.creator.league.LeagueCreator
import org.koin.dsl.module

val leagueCreatorModule = module {
    factory { LeagueCreator() }
}