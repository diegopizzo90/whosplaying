package com.diegopizzo.database.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.diegopizzo.database.dao.FixtureDao
import com.diegopizzo.database.dao.LeagueDao
import com.diegopizzo.database.dao.StandingsDao
import com.diegopizzo.database.entity.FixtureEntity
import com.diegopizzo.database.entity.LeagueEntity
import com.diegopizzo.database.entity.StandingsEntity

@Database(
    entities = [LeagueEntity::class, FixtureEntity::class, StandingsEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun leagueDao(): LeagueDao
    abstract fun fixtureDao(): FixtureDao
    abstract fun standingsDao(): StandingsDao
}