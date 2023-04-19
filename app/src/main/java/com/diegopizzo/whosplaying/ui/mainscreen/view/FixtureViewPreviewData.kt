package com.diegopizzo.whosplaying.ui.mainscreen.view

import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.whosplaying.ui.component.datepickerslider.createDatePickerSliderModel

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
)

private val fixtureDataModel3 = fixtureDataModel.copy(
    homeTeam = "Cagliari",
    awayTeam = "Verona",
)

val fixtures = listOf(fixtureDataModel, fixtureDataModel2, fixtureDataModel3)
val datePickerSliderModel = createDatePickerSliderModel()