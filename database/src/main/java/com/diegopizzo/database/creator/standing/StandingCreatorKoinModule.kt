package com.diegopizzo.database.creator.standing

import org.koin.dsl.module

val standingCreatorModule = module {
    factory { StandingCreator() }
}