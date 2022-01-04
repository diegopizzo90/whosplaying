package com.diegopizzo.repository.standings

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val standingsRepositoryModule = module {
    factory<IStandingsRepository> { StandingsRepository(get(), get(), Dispatchers.IO, get(), get()) }
}