package com.diegopizzo.network.model

import com.diegopizzo.network.model.EventStatistics.StatisticsFormat.INT
import com.diegopizzo.network.model.EventStatistics.StatisticsFormat.PERCENT

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
    val events: List<SingleEvent> = emptyList(),
    val statistics: List<EventStatistics> = emptyList(),
    val lineups: LineupsDataModel? = null,
    val headToHead: List<HeadToHeadDataModel> = emptyList()
) {
    companion object {
        private const val NO_TEAM = "No Team"
    }
}

data class SingleEvent(
    val elapsedEvent: String,
    val idTeamEvent: Long,
    val mainPlayer: String,
    val secondPlayer: String?,
    val type: EventType,
    val detail: EventTypeDetail
)

data class EventStatistics(
    val idTeamHome: Long,
    val idTeamAway: Long,
    val type: StatisticsType? = null,
    val valueTeamHome: String,
    val valueTeamAway: String,
    val percentageValueTeamHome: Float,
    val percentageValueTeamAway: Float,
) {

    enum class StatisticsType(val value: String, val format: StatisticsFormat = INT) {
        SHOTS_ON_GOAL("Shots on Goal"), SHOTS_OFF_GOAL("Shots off Goal"),
        TOTAL_SHOTS("Total Shots"), BLOCKED_SHOTS("Blocked Shots"),
        SHOTS_INSIDE_BOX("Shots insidebox"), SHOTS_OUTSIDE_BOX("Shots outsidebox"),
        FOULS("Fouls"), CORNER_KICKS("Corner Kicks"), OFFSIDES("Offsides"),
        BALL_POSSESSION("Ball Possession", PERCENT), YELLOW_CARDS("Yellow Cards"),
        RED_CARDS("Red Cards"), GOALKEEPER_SAVES("Goalkeeper Saves"),
        TOTAL_PASSES("Total passes"), PASSES_ACCURATE("Passes accurate"),
        PASSES("Passes %", PERCENT);

        companion object {
            fun getByValue(value: String) = values().firstOrNull { it.value == value }
        }
    }

    enum class StatisticsFormat {
        INT, PERCENT
    }
}

data class LineupsDataModel(
    val homeTeamLineup: TeamLineup,
    val awayTeamLineup: TeamLineup
) {
    data class TeamLineup(
        val teamId: Long,
        val teamName: String,
        val coachName: String,
        val formation: String,
        val startEleven: List<PlayerDataModel>,
        val substitutions: List<PlayerDataModel>
    )

    data class PlayerDataModel(val id: Long, val name: String, val number: String, val pos: String)
}