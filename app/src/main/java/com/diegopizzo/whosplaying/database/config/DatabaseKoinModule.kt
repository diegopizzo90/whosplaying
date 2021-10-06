package com.diegopizzo.whosplaying.database.config

import android.app.Application
import androidx.room.Room
import com.diegopizzo.whosplaying.database.dao.FixtureDao
import com.diegopizzo.whosplaying.database.dao.LeagueDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        provideDatabase(androidApplication())
    }
}

val leagueDaoModule = module {
    single { provideLeagueDao(get()) }
}

val fixtureDaoModule = module {
    single { provideFixtureDao(get()) }
}

private fun provideDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(application, AppDatabase::class.java, "whosplaying-db")
        .allowMainThreadQueries()
        .build()
}

private fun provideLeagueDao(database: AppDatabase): LeagueDao {
    return database.leagueDao()
}

private fun provideFixtureDao(database: AppDatabase): FixtureDao {
    return database.fixtureDao()
}