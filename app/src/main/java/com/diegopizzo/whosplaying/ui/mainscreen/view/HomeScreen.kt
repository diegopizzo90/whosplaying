package com.diegopizzo.whosplaying.ui.mainscreen.view

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.whosplaying.ui.detailsscreen.FixtureDetailsContent
import com.diegopizzo.whosplaying.ui.mainscreen.MainViewModel
import com.diegopizzo.whosplaying.ui.mainscreen.navigation.*
import com.diegopizzo.whosplaying.ui.mainscreen.navigation.Destination.*
import com.diegopizzo.whosplaying.ui.standings.StandingsContent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = koinViewModel(),
) {
    val navController = rememberNavController()

    NavigationEffects(
        navigationChannel = mainViewModel.navigationChannel,
        navHostController = navController,
    )
    NavHost(
        navController = navController,
        startDestination = HomeScreen,
    ) {
        composable(destination = HomeScreen) {
            HomeContent()
        }
        composable(destination = StandingsScreen) { backStackEntry ->
            val leagueName = backStackEntry.arguments?.getString(LEAGUE_NAME_KEY)
            StandingsContent(leagueName?.let { LeagueName.valueOf(it) })
        }
        composable(destination = DetailsScreen) { backStackEntry ->
            val fixtureId = backStackEntry.arguments?.getString(FIXTURE_ID_KEY)
            FixtureDetailsContent(fixtureId)
        }
    }
}

@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }
                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }
            }
        }
    }
}