package com.diegopizzo.network.model

data class Status(val long: String, val short: String, val elapsed: Int?)
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

enum class StatusValue(val short: String, val long: String) {
    TIME_TO_BE_DEFINED("TBD", "Time To Be Defined"),
    NOT_STARTED("NS", "Not Started"),
    FIRST_HALF("1H", "First Half, Kick Off"),
    HALF_TIME("HT", "Halftime"),
    SECOND_HALF("2H", "Second Half, 2nd Half Started"),
    EXTRA_TIME("ET", "Extra Time"),
    PENALTY_IN_PROGRESS("P", "Penalty In Progress"),
    MATCH_FINISHED("FT", "Match Finished"),
    MATCH_FINISHED_EXTRA_TIME("AET", "Match Finished After Extra Time"),
    MATCH_FINISHED_PENALTY("PEN", "Match Finished After Penalty"),
    BREAK_TIME("BT", "Break Time (in Extra Time)"),
    MATCH_SUSPENDED("SUSP", "Match Suspended"),
    MATCH_INTERRUPTED("INT", "Match Interrupted"),
    MATCH_POSTPONED("PST", "Match Postponed"),
    MATCH_CANCELED("CANC", "Match Cancelled"),
    MATCH_ABANDONED("ABD", "Match Abandoned"),
    TECHNICAL_LOSS("AWD", "Technical Loss"),
    WALKOVER("WO", "WalkOver"),
    LIVE("LIVE", "In Progress"),
    NOT_AVAILABLE("NA", "Not Available");

    companion object {
        fun getStatusValue(stringValue: String?): StatusValue {
            return values().firstOrNull { it.short == stringValue } ?: NOT_AVAILABLE
        }
    }
}