package com.diegopizzo.database.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.diegopizzo.database.dao.FixtureDao
import com.diegopizzo.database.dao.LeagueDao
import com.diegopizzo.database.entity.FixtureEntity
import com.diegopizzo.database.entity.LeagueEntity

@Database(entities = [LeagueEntity::class, FixtureEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun leagueDao(): LeagueDao
    abstract fun fixtureDao(): FixtureDao
}