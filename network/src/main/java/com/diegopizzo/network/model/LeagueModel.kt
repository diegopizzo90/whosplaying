package com.diegopizzo.network.model

data class LeagueModel(val response: List<LeagueResponse>)
data class LeagueResponse(val league: LeagueInfo, val country: CountryInfo)
data class LeagueInfo(val id: Int, val name: String, val type: String, val logo: String)
data class CountryInfo(val name: String, val code: String)