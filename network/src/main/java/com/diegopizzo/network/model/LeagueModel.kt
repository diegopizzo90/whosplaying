package com.diegopizzo.network.model

data class LeagueModel(val response: List<LeagueResponse>)
data class LeagueResponse(val league: LeagueInfo)
data class LeagueInfo(val id: Int, val name: String, val logo: String)