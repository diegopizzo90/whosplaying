package com.diegopizzo.whosplaying.ui.mainscreen

import androidx.lifecycle.ViewModel
import com.diegopizzo.whosplaying.ui.mainscreen.navigation.IAppNavigator

class MainViewModel(appNavigator: IAppNavigator) : ViewModel() {
    val navigationChannel = appNavigator.navigationChannel
}