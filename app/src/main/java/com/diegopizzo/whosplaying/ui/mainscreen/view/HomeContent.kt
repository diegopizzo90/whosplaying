package com.diegopizzo.whosplaying.ui.mainscreen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.network.interactor.league.CountryCode
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme
import com.diegopizzo.whosplaying.ui.component.attr.blueDark
import com.diegopizzo.whosplaying.ui.component.attr.blueDark3
import com.diegopizzo.whosplaying.ui.component.attr.white
import com.diegopizzo.whosplaying.ui.component.common.MediumText
import com.diegopizzo.whosplaying.ui.component.common.MyAppTopBar
import com.diegopizzo.whosplaying.ui.component.datepickerslider.DatePickerSlider
import com.diegopizzo.whosplaying.ui.component.datepickerslider.DatePickerSliderModel
import com.diegopizzo.whosplaying.ui.mainscreen.BottomNavScreen.*
import com.diegopizzo.whosplaying.ui.mainscreen.HomeViewState
import com.diegopizzo.whosplaying.ui.mainscreen.ViewEffect
import com.diegopizzo.whosplaying.ui.mainscreen.view.FixtureViewPreviewData.datePickerSliderModel
import com.diegopizzo.whosplaying.ui.mainscreen.view.FixtureViewPreviewData.fixtures

@Composable
fun HomeContent(
    viewData: HomeViewState,
    viewEffect: ViewEffect,
    onBottomMenuNavigationSelected: (alpha2Code: CountryCode) -> Unit = {},
    onDaySelected: (daySelected: DatePickerSliderModel) -> Unit = {},
    onStandingsClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            MyAppTopBar(
                title = stringResource(R.string.app_name),
                navigationOnClick = {
                    onBackClicked()
                },
                icon = Icons.Default.ArrowBack,
                actions = {
                    IconButton(onClick = {
                        onStandingsClicked()
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
            BottomNavigation(
                backgroundColor = blueDark,
                contentColor = white,
            ) {
                viewData.bottomNavItems.forEach { navItem ->
                    BottomNavigationItem(
                        selected = viewData.leagueSelected == navItem.id,
                        onClick = {
                            onBottomMenuNavigationSelected(navItem.id.alpha2Code)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(navItem.itemIcon),
                                contentDescription = stringResource(navItem.itemName)
                            )
                        },
                        enabled = true,
                        label = {
                            MediumText(text = stringResource(navItem.itemName))
                        },
                        alwaysShowLabel = true,
                        selectedContentColor = blueDark3,
                        unselectedContentColor = blueDark,
                    )
                }
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            DatePickerSlider(
                dateSliderData = viewData.datePickerSliderModel,
                indexItemSelected = viewData.indexDateSelected,
                onDaySelected = { daySelected ->
                    onDaySelected(daySelected)
                },
            )
            FixtureView(
                state = viewEffect,
                viewData = viewData.fixtures
            )
        }
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    WhosPlayingTheme {
        HomeContent(
            viewData = HomeViewState(
                fixtures = fixtures,
                leagueCountrySelected = CountryCode.ITALY,
                leagueSelected = LeagueName.SERIE_A,
                dateSelected = null,
                updateFixture = false,
                datePickerSliderModel = datePickerSliderModel,
                indexDateSelected = 0,
                bottomNavItems = listOf(SerieA, PremierLeague, LaLiga, Bundesliga, Ligue1)
            ),
            viewEffect = ViewEffect.ShowSuccessResult,
        )
    }
}
