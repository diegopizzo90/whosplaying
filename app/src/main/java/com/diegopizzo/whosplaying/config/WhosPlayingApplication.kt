package com.diegopizzo.whosplaying.config

import android.app.Application
import com.diegopizzo.database.config.databaseModule
import com.diegopizzo.database.config.fixtureDaoModule
import com.diegopizzo.database.config.leagueDaoModule
import com.diegopizzo.database.creator.fixture.fixtureCreatorModule
import com.diegopizzo.network.cache.event.config.eventInteractorCacheModule
import com.diegopizzo.network.cache.fixture.config.fixtureInteractorCacheModule
import com.diegopizzo.network.creator.event.eventModelCreatorModule
import com.diegopizzo.network.creator.fixture.fixtureModelCreatorModule
import com.diegopizzo.network.interactor.event.eventInteractorModule
import com.diegopizzo.network.interactor.fixture.fixtureInteractorModule
import com.diegopizzo.network.interactor.league.leagueInteractorModule
import com.diegopizzo.network.service.retrofitModule
import com.diegopizzo.whosplaying.BuildConfig
import com.diegopizzo.whosplaying.database.creator.league.leagueCreatorModule
import com.diegopizzo.whosplaying.repository.event.eventRepositoryModule
import com.diegopizzo.whosplaying.repository.fixture.fixtureRepositoryModule
import com.diegopizzo.whosplaying.repository.league.leagueRepositoryModule
import com.diegopizzo.whosplaying.ui.detailsscreen.config.detailsScreenViewModelModule
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
                retrofitModule(BuildConfig.BASE_URL, BuildConfig.API_KEY_VALUE),
                fixtureInteractorCacheModule,
                eventInteractorCacheModule,
                eventModelCreatorModule,
                eventRepositoryModule,
                eventInteractorModule,
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
                mainViewModelModule,
                detailsScreenViewModelModule
            )
        }
    }
}