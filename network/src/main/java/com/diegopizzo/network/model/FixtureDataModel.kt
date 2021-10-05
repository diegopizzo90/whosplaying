package com.diegopizzo.network.model

data class FixtureDataModel(
    val dateEvent: String,
    val timeEvent: String,
    val status: String,
    val homeTeam: String,
    val awayTeam: String,
    val logoHomeTeam: String,
    val logoAwayTeam: String,
    val goalHomeTeam: String,
    val goalAwayTeam: String
)