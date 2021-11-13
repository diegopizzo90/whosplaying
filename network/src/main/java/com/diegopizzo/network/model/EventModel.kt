package com.diegopizzo.network.model

import com.google.gson.annotations.SerializedName

data class Time(val elapsed: Int, val extra: Int?)
data class TeamEvent(val id: Long, val name: String, val logo: String)
data class Player(val id: Long, val name: String)
data class Assist(val id: Long?, val name: String?)
data class Event(
    val time: Time,
    val team: TeamEvent,
    val player: Player,
    val assist: Assist,
    val type: EventType,
    val detail: EventTypeDetail
)

data class LeagueDetails(
    val id: Int,
    val name: String,
    val logo: String,
    val season: Int,
    val round: String
)

data class ResponseEvents(
    val fixture: Fixture,
    val league: LeagueDetails,
    val teams: Teams,
    val goals: Goals,
    val events: List<Event>
)

data class EventModel(val response: List<ResponseEvents>)

enum class EventType {
    @SerializedName("Goal")
    GOAL,

    @SerializedName("subst")
    SUBSTITUTION,

    @SerializedName("Card")
    CARD,

    @SerializedName("Var")
    VAR
}

enum class EventTypeDetail(val detail: String) {
    @SerializedName("Normal Goal")
    NORMAL_GOAL("Normal Goal"),

    @SerializedName("Own Goal")
    OWN_GOAL("Own Goal"),

    @SerializedName("Penalty")
    PENALTY("Penalty"),

    @SerializedName("Missed Penalty")
    MISSED_PENALTY("Missed Penalty"),

    @SerializedName("Yellow Card")
    YELLOW_CARD("Yellow Card"),

    @SerializedName("Second Yellow card")
    SECOND_YELLOW_CARD("Second Yellow card"),

    @SerializedName("Red Card")
    RED_CARD("Red Card"),

    @SerializedName("Goal cancelled")
    GOAL_CANCELLED("Goal cancelled"),

    @SerializedName("Card upgrade")
    CARD_UPGRADE("Card upgrade"),

    @SerializedName("Goal confirmed")
    GOAL_CONFIRMED("Goal confirmed"),

    @SerializedName("Penalty confirmed")
    PENALTY_CONFIRMED("Penalty confirmed"),

    @SerializedName("Substitution 1")
    SUBSTITUTION_1("Substitution 1"),

    @SerializedName("Substitution 2")
    SUBSTITUTION_2("Substitution 2"),

    @SerializedName("Substitution 3")
    SUBSTITUTION_3("Substitution 3"),

    @SerializedName("Substitution 4")
    SUBSTITUTION_4("Substitution 4"),

    @SerializedName("Substitution 5")
    SUBSTITUTION_5("Substitution 5"),

    @SerializedName("Substitution 6")
    SUBSTITUTION_6("Substitution 6"),

    NOT_AVAILABLE("Not available")
}