package com.diegopizzo.database.creator.standings

import org.koin.dsl.module

val standingsCreatorModule = module {
    factory { StandingsCreator() }
}