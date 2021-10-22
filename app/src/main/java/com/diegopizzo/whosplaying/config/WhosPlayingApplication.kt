package com.diegopizzo.whosplaying.config

import android.app.Application
import com.diegopizzo.network.cache.fixture.config.fixtureInteractorCacheModule
import com.diegopizzo.network.creator.fixtureModelCreatorModule
import com.diegopizzo.network.interactor.fixture.fixtureInteractorModule
import com.diegopizzo.network.interactor.league.leagueInteractorModule
import com.diegopizzo.database.config.databaseModule
import com.diegopizzo.database.config.fixtureDaoModule
import com.diegopizzo.database.config.leagueDaoModule
import com.diegopizzo.database.creator.fixture.fixtureCreatorModule
import com.diegopizzo.whosplaying.database.creator.league.leagueCreatorModule
import com.diegopizzo.whosplaying.repository.fixture.fixtureRepositoryModule
import com.diegopizzo.whosplaying.repository.league.leagueRepositoryModule
import com.diegopizzo.whosplaying.ui.mainscreen.config.mainViewModelModule
import com.diegopizzo.whosplaying.ui.splashscreen.config.splashScreenViewModelModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WhosPlayingApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin {
            androidContext(this@WhosPlayingApplication)
            modules(
                retrofitModule,
                fixtureInteractorCacheModule,
                databaseModule,
                leagueDaoModule,
                fixtureDaoModule,
                leagueCreatorModule,
                fixtureCreatorModule,
                fixtureModelCreatorModule,
                fixtureInteractorModule,
                leagueInteractorModule,
                leagueRepositoryModule,
                fixtureRepositoryModule,
                splashScreenViewModelModule,
                mainViewModelModule
            )
        }
    }
}