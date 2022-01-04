package com.diegopizzo.network.creator.standings

import org.koin.dsl.module

val standingsModelCreatorModule = module {
    factory { StandingsModelCreator() }
}