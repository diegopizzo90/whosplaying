package com.diegopizzo.network.model

data class HeadToHeadDataModel(
    val date: String,
    val nameHome: String,
    val logoHome: String,
    val scoreHomeTeam: String,
    val nameAway: String,
    val logoAway: String,
    val scoreAwayTeam: String
)