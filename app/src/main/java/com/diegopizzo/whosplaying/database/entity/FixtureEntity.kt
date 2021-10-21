package com.diegopizzo.whosplaying.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fixture")
data class FixtureEntity(
    @PrimaryKey val fixtureId: Long,
    val dateEvent: String,
    val timeEvent: String,
    val status: String,
    val elapsed: String?,
    val homeTeam: String,
    val awayTeam: String,
    val logoHomeTeam: String,
    val logoAwayTeam: String,
    val goalHomeTeam: String,
    val goalAwayTeam: String,
    val fixtureLeagueId: Long
)