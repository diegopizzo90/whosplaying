package com.diegopizzo.whosplaying.ui.mainscreen.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel

class AppNavigator : IAppNavigator {

    override val navigationChannel = Channel<NavigationIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    override fun navigateBack(route: String?, isInclusive: Boolean) {
        navigationChannel.trySend(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = isInclusive
            )
        )
    }

    override fun navigateTo(
        route: String,
        popUpToRoute: String?,
        isInclusive: Boolean,
        isSingleTop: Boolean
    ) {
        navigationChannel.trySend(
            NavigationIntent.NavigateTo(
                route = route,
                popUpToRoute = popUpToRoute,
                inclusive = isInclusive,
                isSingleTop = isSingleTop,
            )
        )
    }
}