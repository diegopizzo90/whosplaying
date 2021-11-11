package com.diegopizzo.network.model

data class EventDataModel(
    val fixtureId: Long = 0L,
    val dateTimeEventUtc: String = "",
    val status: StatusValue = StatusValue.NOT_AVAILABLE,
    val elapsed: String? = null,
    val homeTeamId: Long = 0L,
    val homeTeam: String = NO_TEAM,
    val awayTeamId: Long = 0L,
    val awayTeam: String = NO_TEAM,
    val logoHomeTeam: String = "",
    val logoAwayTeam: String = "",
    val scoreHomeTeam: String = "0",
    val scoreAwayTeam: String = "0",
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

private const val NO_TEAM = "No Team"