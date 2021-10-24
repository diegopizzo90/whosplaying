package com.diegopizzo.network.interactor.event

import org.koin.dsl.module

val eventInteractorModule = module {
    factory<IEventInteractor> { EventInteractor(get(), get()) }
}