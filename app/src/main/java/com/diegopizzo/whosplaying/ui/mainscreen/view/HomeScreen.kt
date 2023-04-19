package com.diegopizzo.whosplaying.ui.mainscreen.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.diegopizzo.whosplaying.ui.detailsscreen.DetailsScreenViewModel
import com.diegopizzo.whosplaying.ui.detailsscreen.FixtureDetailsContent
import com.diegopizzo.whosplaying.ui.mainscreen.HomeViewModel
import com.diegopizzo.whosplaying.ui.mainscreen.navigation.Destination
import com.diegopizzo.whosplaying.ui.mainscreen.navigation.Destination.*
import com.diegopizzo.whosplaying.ui.mainscreen.navigation.NavHost
import com.diegopizzo.whosplaying.ui.mainscreen.navigation.composable
import com.diegopizzo.whosplaying.ui.standings.StandingsContent
import com.diegopizzo.whosplaying.ui.standings.StandingsViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    standingsViewModel: StandingsViewModel,
    detailsScreenViewModel: DetailsScreenViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Destination = HomeScreen,
) {
    val viewEffectState = homeViewModel.viewEffects().observeAsState().value ?: return
    val viewDataState = homeViewModel.viewStates().observeAsState().value ?: return

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(destination = HomeScreen) {
            HomeContent(
                viewData = viewDataState,
                viewEffect = viewEffectState,
                onBottomMenuNavigationSelected = { countryCode ->
                    homeViewModel.onBottomMenuNavigationSelected(countryCode)
                },
                onDaySelected = { dateSelected ->
                    homeViewModel.onDaySelected(dateSelected)
                },
                onStandingsClicked = {
                    navController.navigate(StandingsScreen.fullRoute)
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(destination = StandingsScreen) {
            StandingsContent(
                viewModel = standingsViewModel,
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(destination = DetailsScreen) {
            FixtureDetailsContent(
                viewModel = detailsScreenViewModel,
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}