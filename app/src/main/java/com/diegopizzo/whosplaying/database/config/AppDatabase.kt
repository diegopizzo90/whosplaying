package com.diegopizzo.whosplaying.database.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.diegopizzo.whosplaying.database.dao.FixtureDao
import com.diegopizzo.whosplaying.database.dao.LeagueDao
import com.diegopizzo.whosplaying.database.entity.LeagueEntity

@Database(entities = [LeagueEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun leagueDao(): LeagueDao
    abstract fun fixtureDao(): FixtureDao
}