package com.diegopizzo.network.cache.standing

import org.koin.dsl.module

val standingInteractorCacheModule = module {
    single<IStandingInteractorCache> { StandingInteractorCache(get()) }
}