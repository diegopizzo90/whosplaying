package com.diegopizzo.whosplaying.ui.mainscreen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.blueDark
import com.diegopizzo.whosplaying.ui.component.attr.blueDark3
import com.diegopizzo.whosplaying.ui.component.attr.white
import com.diegopizzo.whosplaying.ui.component.common.MediumText
import com.diegopizzo.whosplaying.ui.component.common.MyAppTopBar
import com.diegopizzo.whosplaying.ui.component.datepickerslider.DatePickerSlider
import com.diegopizzo.whosplaying.ui.mainscreen.IMainViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: IMainViewModel,
) {
    val viewEffectState = viewModel.viewEffects().observeAsState().value ?: return
    val viewDataState = viewModel.viewStates().observeAsState().value ?: return

    Scaffold(
        modifier = modifier,
        topBar = {
            MyAppTopBar(
                title = stringResource(R.string.app_name),
                navigationOnClick = {
                },
                actions = {
                    IconButton(onClick = {

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
                viewDataState.bottomNavItems.forEach { navItem ->
                    BottomNavigationItem(
                        selected = viewDataState.leagueSelected == navItem.id,
                        onClick = {
                            viewModel.onBottomMenuNavigationSelected(navItem.id.alpha2Code)
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
                dateSliderData = viewDataState.datePickerSliderModel,
                indexItemSelected = viewDataState.indexDateSelected,
                onDaySelected = { daySelected ->
                    viewModel.onDaySelected(daySelected)
                },
            )
            FixtureView(
                state = viewEffectState,
                viewData = viewDataState.fixtures
            )
        }
    }
}