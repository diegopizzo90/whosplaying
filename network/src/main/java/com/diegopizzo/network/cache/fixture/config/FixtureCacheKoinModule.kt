package com.diegopizzo.network.cache.fixture.config

import com.diegopizzo.network.cache.fixture.FixtureInteractorCache
import com.diegopizzo.network.cache.fixture.IFixtureInteractorCache
import org.koin.dsl.module

val fixtureInteractorCacheModule = module {
    single<IFixtureInteractorCache> { FixtureInteractorCache(get()) }
}

internal const val DEFAULT_DURATION_MILLIS = 120000L //120 seconds