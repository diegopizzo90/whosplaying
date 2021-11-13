package com.diegopizzo.network.creator.event

import com.diegopizzo.network.model.*

class EventModelCreator {

    fun toEventDataModel(model: EventModel?): EventDataModel? {
        if (model == null) return null
        val response = model.response.first()
        val fixture = response.fixture
        val status = fixture.status
        val homeTeam = response.teams.home
        val awayTeam = response.teams.away
        val goals = response.goals
        val events = response.events

        return EventDataModel(
            fixtureId = fixture.id,
            dateTimeEventUtc = fixture.date,
            status = StatusValue.getStatusValue(fixture.status.short),
            elapsed = status.elapsed?.toString(),
            homeTeamId = homeTeam.id,
            homeTeam = homeTeam.name,
            logoHomeTeam = homeTeam.logo,
            awayTeamId = awayTeam.id,
            awayTeam = awayTeam.name,
            logoAwayTeam = awayTeam.logo,
            scoreHomeTeam = goals.home?.toString() ?: "0",
            scoreAwayTeam = goals.away?.toString() ?: "0",
            events = events.map { toSingleEvent(it) }
        )
    }

    private fun toSingleEvent(event: Event): SingleEvent {
        return SingleEvent(
            elapsedEvent = if (event.time.extra == null) "${event.time.elapsed}′" else "${event.time.elapsed}′+${event.time.extra}′",
            idTeamEvent = event.team.id,
            mainPlayer = event.player.name,
            secondPlayer = event.assist.name,
            type = event.type,
            detail = event.detail ?: EventTypeDetail.NOT_AVAILABLE
        )
    }
}