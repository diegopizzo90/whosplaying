package com.diegopizzo.network.model

import com.google.gson.annotations.SerializedName

data class GoalsInfo(@SerializedName("for") val scored: String, val against: String)
data class AllStandingInfo(
    val played: String,
    val win: String,
    val draw: String,
    val lose: String,
    val goals: GoalsInfo
)

data class Standing(
    val rank: String,
    val team: TeamEvent,
    val points: String,
    val goalsDiff: String,
    val form: String,
    val all: AllStandingInfo,
    val update: String
)

data class LeagueData(val standings: List<List<Standing>>)
data class ResponseStanding(val league: LeagueData)
data class StandingModel(val response: List<ResponseStanding>)