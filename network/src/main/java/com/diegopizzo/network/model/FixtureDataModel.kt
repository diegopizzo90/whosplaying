package com.diegopizzo.network.model

data class FixtureDataModel(
    val fixtureId: Long,
    val dateTimeEventUtc: String,
    val status: String,
    val elapsed: String?,
    val homeTeam: String,
    val awayTeam: String,
    val logoHomeTeam: String,
    val logoAwayTeam: String,
    val goalHomeTeam: String,
    val goalAwayTeam: String
)