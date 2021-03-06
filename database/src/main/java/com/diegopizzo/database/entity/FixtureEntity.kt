package com.diegopizzo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "fixture")
data class FixtureEntity(
    @PrimaryKey val fixtureId: Long,
    val dateTimeEvent: ZonedDateTime,
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