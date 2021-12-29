package com.diegopizzo.network.interactor.standing

import org.koin.dsl.module

val standingInteractorModule = module {
    factory<IStandingInteractor> { StandingInteractor(get(), get()) }
}