package com.diegopizzo.repository.standing

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val standingRepositoryModule = module {
    factory<IStandingRepository> { StandingRepository(get(), get(), Dispatchers.IO, get(), get()) }
}