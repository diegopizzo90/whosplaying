package com.diegopizzo.network.model

data class EventDataModel(
    val fixtureId: Long,
    val dateTimeEventUtc: String,
    val status: StatusValue?,
    val elapsed: String?,
    val homeTeamId: Long,
    val homeTeam: String,
    val awayTeamId: Long,
    val awayTeam: String,
    val logoHomeTeam: String,
    val logoAwayTeam: String,
    val scoreHomeTeam: String?,
    val scoreAwayTeam: String?,
    val leagueDetails: LeagueDetails,
    val events: List<SingleEvent> = emptyList()
)

data class SingleEvent(
    val elapsedEvent: String,
    val idTeamEvent: Long,
    val mainPlayer: String,
    val secondPlayer: String?,
    val type: EventType,
    val detail: EventTypeDetail
)