package com.diegopizzo.network.cache.event.config

import com.diegopizzo.network.cache.event.EventInteractorCache
import com.diegopizzo.network.cache.event.IEventInteractorCache
import org.koin.dsl.module

val eventInteractorCacheModule = module {
    single<IEventInteractorCache> { EventInteractorCache(get()) }
}