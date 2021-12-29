package com.diegopizzo.network.creator.standing

import org.koin.dsl.module

val standingModelCreatorModule = module {
    factory { StandingModelCreator() }
}