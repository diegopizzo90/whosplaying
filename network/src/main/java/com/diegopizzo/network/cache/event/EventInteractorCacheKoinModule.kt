package com.diegopizzo.network.cache.event

import org.koin.dsl.module

val eventInteractorCacheModule = module {
    single<IEventInteractorCache> { EventInteractorCache(get()) }
}