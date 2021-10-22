package com.diegopizzo.database.creator.fixture

import com.diegopizzo.database.creator.fixture.FixtureCreator
import org.koin.dsl.module

val fixtureCreatorModule = module {
    factory { FixtureCreator() }
}