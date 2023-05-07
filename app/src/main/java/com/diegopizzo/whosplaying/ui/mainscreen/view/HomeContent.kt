package com.diegopizzo.whosplaying.ui.mainscreen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.common.MyAppTopBar
import com.diegopizzo.whosplaying.ui.component.datepickerslider.DatePickerSlider
import com.diegopizzo.whosplaying.ui.mainscreen.HomeViewModel
import com.diegopizzo.whosplaying.ui.mainscreen.view.bottomnavigation.BottomNavigationView
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    val viewDataState = homeViewModel.viewStates().observeAsState().value ?: return

    LaunchedEffect(key1 = viewDataState.leagueCountrySelected, key2 = viewDataState.dateSelected) {
        homeViewModel.getFixturesByLeagueName(
            viewDataState.leagueCountrySelected,
            viewDataState.dateSelected
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            MyAppTopBar(
                title = stringResource(R.string.app_name),
                actions = {
                    IconButton(onClick = {
                        homeViewModel.onStandingsClicked(viewDataState.leagueSelected)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_ranking),
                            contentDescription = "",
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationView(
                bottomNavItems = viewDataState.bottomNavItems,
                leagueSelected = viewDataState.leagueSelected,
                onItemClicked = { navItem ->
                    homeViewModel.onBottomMenuNavigationSelected(navItem.id)
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            DatePickerSlider(
                dateSliderData = viewDataState.datePickerSliderModel,
                indexItemSelected = viewDataState.indexDateSelected,
                onDaySelected = { daySelected ->
                    homeViewModel.onDaySelected(daySelected)
                },
            )
            FixtureView(
                state = viewDataState.screenResult,
                viewData = viewDataState.fixtures,
                onFixtureClicked = { fixtureId ->
                    homeViewModel.onFixtureClicked(fixtureId)
                }
            )
        }
    }
}
