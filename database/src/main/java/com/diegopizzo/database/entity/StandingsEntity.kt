package com.diegopizzo.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "standings", indices = [Index(value = ["nameTeam"], unique = true)])
data class StandingsEntity(
    @PrimaryKey val idTeam: String,
    val nameTeam: String,
    val logoTeam: String,
    val rank: String,
    val points: String,
    val goalsDiff: String,
    val form: String,
    val played: String,
    val win: String,
    val draw: String,
    val lose: String,
    val scored: String,
    val against: String,
    val standingsLeagueId: Long
)