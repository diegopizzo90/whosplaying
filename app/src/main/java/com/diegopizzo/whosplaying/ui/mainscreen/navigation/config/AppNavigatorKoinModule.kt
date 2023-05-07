package com.diegopizzo.whosplaying.ui.mainscreen.navigation.config

import com.diegopizzo.whosplaying.ui.mainscreen.navigation.AppNavigator
import com.diegopizzo.whosplaying.ui.mainscreen.navigation.IAppNavigator
import org.koin.dsl.module

val appNavigatorModule = module {
    single<IAppNavigator> {
        AppNavigator()
    }
}