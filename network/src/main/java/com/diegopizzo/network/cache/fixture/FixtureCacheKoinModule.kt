package com.diegopizzo.network.cache.fixture

import org.koin.dsl.module

val fixtureInteractorCacheModule = module {
    single<IFixtureInteractorCache> { FixtureInteractorCache(get()) }
}