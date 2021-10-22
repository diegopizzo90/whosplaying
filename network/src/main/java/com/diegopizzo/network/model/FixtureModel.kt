package com.diegopizzo.network.model

data class Status(val short: String, val elapsed: Int?)
data class Fixture(
    val id: Long,
    val timezone: String,
    val date: String,
    val status: Status
)

data class Home(val id: Long, val name: String, val logo: String)
data class Away(val id: Long, val name: String, val logo: String)
data class Teams(val home: Home, val away: Away)

data class Goals(val home: Int?, val away: Int?)

data class ResponseFixture(val fixture: Fixture, val teams: Teams, val goals: Goals)

data class FixtureModel(val response: List<ResponseFixture>)

enum class StatusValue(val short: String) {
    TIME_TO_BE_DEFINED("TBD"),
    NOT_STARTED("NS"),
    FIRST_HALF("1H"),
    HALF_TIME("HT"),
    SECOND_HALF("2H"),
    EXTRA_TIME("ET"),
    PENALTY_IN_PROGRESS("P"),
    MATCH_FINISHED("FT"),
    MATCH_FINISHED_EXTRA_TIME("AET"),
    MATCH_FINISHED_PENALTY("PEN"),
    BREAK_TIME("BT"),
    MATCH_SUSPENDED("SUSP"),
    MATCH_INTERRUPTED("INT"),
    MATCH_POSTPONED("PST"),
    MATCH_CANCELED("CANC"),
    MATCH_ABANDONED("ABD"),
    TECHNICAL_LOSS("AWD"),
    WALKOVER("WO"),
    LIVE("LIVE")
}