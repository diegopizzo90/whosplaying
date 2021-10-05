package com.diegopizzo.whosplaying.database.repository.fixture

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val fixtureRepositoryModule = module {
    factory<IFixtureRepository> { FixtureRepository(get(), get(), Dispatchers.IO, get()) }
}