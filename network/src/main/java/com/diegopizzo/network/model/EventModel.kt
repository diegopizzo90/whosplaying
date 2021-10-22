package com.diegopizzo.network.model

internal data class Time(val elapsed: Int)
internal data class TeamEvent(val id: Long, val name: String, val logo: String)
internal data class Player(val id: Long, val name: String)
internal data class Assist(val id: Long?, val name: String?)
internal data class Event(
    val time: Time,
    val team: TeamEvent,
    val player: Player,
    val assist: Assist,
    val type: String,
    val detail: String
)

internal data class LeagueDetails(
    val id: Int,
    val name: String,
    val logo: String,
    val season: String,
    val round: String
)

internal data class ResponseEvents(
    val fixture: Fixture,
    val league: LeagueDetails,
    val teams: Teams,
    val goals: Goals,
    val events: List<Event>
)