package com.diegopizzo.network.creator.fixture

import org.koin.dsl.module

val fixtureModelCreatorModule = module {
    factory { FixtureModelCreator() }
}