package com.diegopizzo.network.creator.event

import com.diegopizzo.network.model.*
import com.diegopizzo.network.model.EventStatistics.StatisticsFormat
import com.diegopizzo.network.model.EventStatistics.StatisticsFormat.PERCENT
import com.diegopizzo.network.model.EventStatistics.StatisticsType
import com.diegopizzo.network.model.LineupsDataModel.PlayerDataModel
import java.math.RoundingMode
import java.text.DecimalFormat

class EventModelCreator {

    fun toEventDataModel(model: EventModel?, statisticsModel: StatisticsModel?, lineupsModel: LineupsModel?): EventDataModel? {
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
            events = events.map { toSingleEvent(it) },
            statistics = statisticsModel?.let { toEventStatistics(it) } ?: emptyList(),
            lineups = toLineupsDataModel(lineupsModel)
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

    private fun toEventStatistics(statisticsModel: StatisticsModel): List<EventStatistics> {
        val response = statisticsModel.response
        val teamsId = response.run {
            Pair(component1().team.id.toLong(), component2().team.id.toLong())
        }

        val homeTeamStatistics = response.first().statistics
        val awayTeamStatistics = response.component2().statistics

        return homeTeamStatistics.zip(awayTeamStatistics) { home, away ->
            val statisticsType = StatisticsType.getByValue(home.type)
            val valueTeamHome = home.value?.filter { it.isDigit() } ?: "0"
            val valueTeamAway = away.value?.filter { it.isDigit() } ?: "0"
            val percentage =
                calculatePercentageStatisticsValues(
                    valueTeamHome.toInt(),
                    valueTeamAway.toInt(),
                    statisticsType?.format
                )

            EventStatistics(
                teamsId.first,
                teamsId.second,
                statisticsType,
                addFormat(valueTeamHome, statisticsType?.format),
                addFormat(valueTeamAway, statisticsType?.format),
                percentage.first,
                percentage.second
            )
        }
    }

    private fun toLineupsDataModel(model: LineupsModel?): LineupsDataModel? {
        if (model == null) return null
        val homeTeamModel = model.response.first()
        val awayTeamModel = model.response.component2()

        fun createLineupPlayer(player: PlayerLineup): PlayerDataModel {
            return PlayerDataModel(player.id, player.name, player.number, player.pos)
        }

        return LineupsDataModel(
            homeTeamLineup = LineupsDataModel.TeamLineup(
                homeTeamModel.team.id,
                homeTeamModel.team.name,
                homeTeamModel.coach.name ?: "",
                homeTeamModel.formation,
                startEleven = homeTeamModel.startXI.map {
                    createLineupPlayer(it.player)
                },
                substitutions = homeTeamModel.substitutes.map {
                    createLineupPlayer(it.player)
                }
            ),
            awayTeamLineup = LineupsDataModel.TeamLineup(
                awayTeamModel.team.id,
                awayTeamModel.team.name,
                awayTeamModel.coach.name ?: "",
                awayTeamModel.formation,
                startEleven = awayTeamModel.startXI.map {
                    createLineupPlayer(it.player)
                },
                substitutions = awayTeamModel.substitutes.map {
                    createLineupPlayer(it.player)
                }
            )
        )
    }

    private fun calculatePercentageStatisticsValues(
        homeValue: Int,
        awayValue: Int,
        format: StatisticsFormat?
    ): Pair<Float, Float> {
        val totalCount = homeValue + awayValue
        val homeValueF =
            if (format == PERCENT) homeValue.toFloat() / 100 else roundOffDecimal(homeValue.toFloat() / totalCount)
        val awayValueF =
            if (format == PERCENT) awayValue.toFloat() / 100 else roundOffDecimal(awayValue.toFloat() / totalCount)
        return Pair(homeValueF, awayValueF)
    }

    private fun roundOffDecimal(number: Float): Float {
        if (number.isNaN()) return 0.0f
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toFloat()
    }

    private fun addFormat(str: String, format: StatisticsFormat?): String {
        return when (format) {
            PERCENT -> "$str%"
            else -> str
        }
    }
}