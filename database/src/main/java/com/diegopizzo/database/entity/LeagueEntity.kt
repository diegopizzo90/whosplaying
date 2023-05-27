package com.diegopizzo.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.diegopizzo.network.interactor.league.LeagueType
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "league", indices = [Index(value = ["name"], unique = true)])
data class LeagueEntity(
    @PrimaryKey val leagueId: Long,
    val name: String,
    val logo: String,
    val countryName: String,
    val countryCode: String,
    val leagueType: LeagueType,
    val lastUpdate: ZonedDateTime?
)