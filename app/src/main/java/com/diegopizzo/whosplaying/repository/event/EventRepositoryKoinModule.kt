package com.diegopizzo.whosplaying.repository.event

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val eventRepositoryModule = module {
    factory<IEventRepository> { EventRepository(get(), Dispatchers.IO) }
}