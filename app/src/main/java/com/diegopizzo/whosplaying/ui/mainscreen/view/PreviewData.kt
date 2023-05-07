package com.diegopizzo.whosplaying.ui.mainscreen.view

import com.diegopizzo.network.model.FixtureDataModel

private val fixtureDataModel = FixtureDataModel(
    fixtureId = 1,
    dateTimeEventUtc = "",
    status = "90'",
    elapsed = "90'",
    homeTeam = "FC Inter",
    awayTeam = "AC Milan",
    logoHomeTeam = "",
    logoAwayTeam = "",
    goalHomeTeam = "0",
    goalAwayTeam = "1",
    isFixtureLive = true
)

private val fixtureDataModel2 = fixtureDataModel.copy(
    homeTeam = "AS Roma",
    awayTeam = "Lazio",
    goalHomeTeam = "",
    goalAwayTeam = "",
    status = "21:00",
    isFixtureLive = false
)

private val fixtureDataModel3 = fixtureDataModel.copy(
    homeTeam = "Cagliari",
    awayTeam = "Verona",
    goalHomeTeam = "0",
    goalAwayTeam = "3",
)

val fixtures = listOf(fixtureDataModel, fixtureDataModel2, fixtureDataModel3)