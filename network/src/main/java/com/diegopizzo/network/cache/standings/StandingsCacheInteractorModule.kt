package com.diegopizzo.network.cache.standings

import org.koin.dsl.module

val standingsInteractorCacheModule = module {
    single<IStandingsInteractorCache> { StandingsInteractorCache(get()) }
}