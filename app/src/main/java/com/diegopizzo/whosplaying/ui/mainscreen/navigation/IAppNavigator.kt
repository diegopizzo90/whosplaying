package com.diegopizzo.whosplaying.ui.mainscreen.navigation

import kotlinx.coroutines.channels.Channel

interface IAppNavigator {
    val navigationChannel: Channel<NavigationIntent>

    suspend fun navigateBack(
        route: String? = null,
        isInclusive: Boolean = false,
    )

    suspend fun navigateTo(
        route: String,
        popUpToRoute: String? = null,
        isInclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )
}

sealed class NavigationIntent {
    data class NavigateBack(
        val route: String? = null,
        val inclusive: Boolean = false,
    ) : NavigationIntent()

    data class NavigateTo(
        val route: String,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false,
    ) : NavigationIntent()
}