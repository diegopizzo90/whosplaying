package com.diegopizzo.network.model

import com.google.gson.annotations.SerializedName

data class GoalsInfo(@SerializedName("for") val scored: String, val against: String)
data class AllStandingsInfo(
    val played: String,
    val win: String,
    val draw: String,
    val lose: String,
    val goals: GoalsInfo
)

data class Standings(
    val rank: String,
    val team: TeamEvent,
    val points: String,
    val goalsDiff: String,
    val form: String,
    val all: AllStandingsInfo,
    val update: String
)

data class LeagueData(val standings: List<List<Standings>>?)
data class ResponseStandings(val league: LeagueData?)
data class StandingsModel(val response: List<ResponseStandings>?)