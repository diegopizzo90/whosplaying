package com.diegopizzo.network.model

data class FixtureH2H(
    val id: Long,
    val timezone: String,
    val date: String
)

data class ResponseHeadToHead(val fixture: FixtureH2H, val teams: Teams, val goals: Goals)
data class HeadToHeadModel(val response: List<ResponseHeadToHead>)