package com.diegopizzo.whosplaying.database.creator.fixture

import org.koin.dsl.module

val fixtureCreatorModule = module {
    factory { FixtureCreator() }
}