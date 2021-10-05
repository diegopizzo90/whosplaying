package com.diegopizzo.network.interactor.fixture

import org.koin.dsl.module

val fixtureInteractorModule = module {
    factory<IFixtureInteractor> { FixtureInteractor(get(), get()) }
}