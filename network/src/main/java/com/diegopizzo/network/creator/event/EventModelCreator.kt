package com.diegopizzo.network.creator.event

import com.diegopizzo.network.model.*
import com.diegopizzo.network.model.EventStatistics.StatisticsFormat
import com.diegopizzo.network.model.EventStatistics.StatisticsFormat.DECIMAL
import com.diegopizzo.network.model.EventStatistics.StatisticsFormat.PERCENT
import com.diegopizzo.network.model.EventStatistics.StatisticsType
import com.diegopizzo.network.model.LineupsDataModel.PlayerDataModel
import java.math.RoundingMode
import java.text.DecimalFormat

class EventModelCreator {

    fun toEventDataModel(
        eventModel: EventModel?,
        statisticsModel: StatisticsModel?,
        lineupsModel: LineupsModel?,
        headToHeadModel: HeadToHeadModel?
    ): EventDataModel {
        val response = eventModel?.response?.first()
        val fixture = response?.fixture
        val status = fixture?.status
        val homeTeam = response?.teams?.home
        val awayTeam = response?.teams?.away
        val goals = response?.goals
        val events = response?.events

        return EventDataModel(
            fixtureId = fixture?.id ?: 0L,
            dateTimeEventUtc = fixture?.date ?: "",
            status = StatusValue.getStatusValue(fixture?.status?.short),
            elapsed = status?.elapsed?.toString(),
            homeTeamId = homeTeam?.id ?: 0L,
            homeTeam = homeTeam?.name ?: "",
            logoHomeTeam = homeTeam?.logo ?: "",
            awayTeamId = awayTeam?.id ?: 0L,
            awayTeam = awayTeam?.name ?: "",
            logoAwayTeam = awayTeam?.logo ?: "",
            scoreHomeTeam = goals?.home?.toString() ?: "0",
            scoreAwayTeam = goals?.away?.toString() ?: "0",
            events = events?.map { toSingleEvent(it) } ?: emptyList(),
            statistics = statisticsModel?.let { toEventStatistics(it) } ?: emptyList(),
            lineups = toLineupsDataModel(lineupsModel),
            headToHead = toHeadToHeadDataModel(headToHeadModel, fixture?.id ?: 0L)
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
        if (response.isEmpty()) return emptyList()
        val teamsId = response.run {
            ifEmpty { emptyList() }
            Pair(component1().team.id.toLong(), component2().team.id.toLong())
        }

        val homeTeamStatistics = response.first().statistics
        val awayTeamStatistics = response.component2().statistics

        return homeTeamStatistics.zip(awayTeamStatistics) { home, away ->
            val statisticsType = StatisticsType.getByValue(home.type)
            val valueTeamHome = home.value?.filter { it.isDigit() || it.isDot() } ?: "0"
            val valueTeamAway = away.value?.filter { it.isDigit() || it.isDot() } ?: "0"
            val percentage =
                calculatePercentageStatisticsValues(
                    valueTeamHome,
                    valueTeamAway,
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

    private fun Char.isDot() = this == '.'

    private fun toLineupsDataModel(model: LineupsModel?): LineupsDataModel? {
        if (model == null || model.response.isEmpty()) return null
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
        homeValue: String,
        awayValue: String,
        format: StatisticsFormat?
    ): Pair<Float, Float> {
        val home = homeValue.toFloat()
        val away = awayValue.toFloat()
        val totalCount = home + away
        return when (format) {
            PERCENT -> Pair(home / 100, away / 100)
            DECIMAL -> Pair(home, away)
            else -> Pair(roundOffDecimal(home / totalCount), roundOffDecimal(away / totalCount))
        }
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

    private fun toHeadToHeadDataModel(
        model: HeadToHeadModel?,
        eventFixtureId: Long
    ): List<HeadToHeadDataModel> {
        if (model == null) return emptyList()
        return model.response.filter { eventFixtureId != it.fixture.id }.map {
            val home = it.teams.home
            val away = it.teams.away
            HeadToHeadDataModel(
                it.fixture.date,
                home.name,
                home.logo,
                it.goals.home?.toString() ?: "0",
                away.name,
                away.logo,
                it.goals.away?.toString() ?: "0"
            )
        }
    }
}