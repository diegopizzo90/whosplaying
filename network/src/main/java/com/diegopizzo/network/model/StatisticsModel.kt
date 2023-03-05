package com.diegopizzo.network.model

data class Team(val id: String, val name: String)
data class Statistics(val type: String, val value: String?)
data class TeamStatistics(val team: Team, val statistics: List<Statistics>)
data class StatisticsModel(val response: List<TeamStatistics>)