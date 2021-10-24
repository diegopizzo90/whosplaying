package com.diegopizzo.network.creator.event

import org.koin.dsl.module

val eventModelCreatorModule = module {
    factory { EventModelCreator() }
}