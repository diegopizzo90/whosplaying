package com.diegopizzo.network.creator

import org.koin.dsl.module

val fixtureModelCreatorModule = module {
    factory { FixtureModelCreator() }
}