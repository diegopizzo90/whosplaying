package com.diegopizzo.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "league", indices = [Index(value = ["name"], unique = true)])
data class LeagueEntity(@PrimaryKey val leagueId: Long, val name: String, val logo: String)