@file:OptIn(ExperimentalPagerApi::class)

package com.diegopizzo.whosplaying.ui.detailsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.network.model.EventDataModel
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme
import com.diegopizzo.whosplaying.ui.component.common.LoadingView
import com.diegopizzo.whosplaying.ui.component.common.MyScaffold
import com.diegopizzo.whosplaying.ui.component.viewpager.TabViewPager
import com.diegopizzo.whosplaying.ui.detailsscreen.TabPager.*
import com.diegopizzo.whosplaying.ui.detailsscreen.event.ComposeEventScoreBoard
import com.diegopizzo.whosplaying.ui.detailsscreen.event.MatchEvents
import com.diegopizzo.whosplaying.ui.detailsscreen.lineups.LineupsView
import com.diegopizzo.whosplaying.ui.detailsscreen.statistics.StatisticsView
import com.google.accompanist.pager.ExperimentalPagerApi
import org.koin.androidx.compose.koinViewModel
import org.threeten.bp.ZoneId

@Composable
fun FixtureDetailsContent(
    fixtureId: String?,
    viewModel: DetailsScreenViewModel = koinViewModel(),
) {
    val viewDataState = viewModel.viewStates().observeAsState().value ?: return

    LaunchedEffect(key1 = fixtureId) {
        fixtureId?.toLong()?.let { viewModel.getFixtureEventDetails(it) }
    }

    when (viewDataState.screenResult) {
        DetailsScreenResult.ShowProgressBar -> LoadingView()
        DetailsScreenResult.ShowSuccessResult -> {
            MyScaffold(
                navigationOnClick = {
                    viewModel.onBackClicked()
                },
            ) {
                FixtureDetailsView(
                    modifier = Modifier.padding(it),
                    dataModel = viewDataState.eventDataModel,
                    tabViewDataList = viewDataState.tabViewDataList,
                    zoneId = ZoneId.systemDefault(),
                )
            }
        }
        else -> Unit
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun FixtureDetailsView(
    modifier: Modifier = Modifier,
    dataModel: EventDataModel,
    tabViewDataList: List<TabPager>,
    zoneId: ZoneId? = null
) {
    Column(modifier = modifier) {
        ComposeEventScoreBoard(
            dataModel.logoHomeTeam,
            dataModel.homeTeam,
            dataModel.scoreHomeTeam,
            dataModel.logoAwayTeam,
            dataModel.awayTeam,
            dataModel.scoreAwayTeam,
            dataModel.status.long
        )
        TabViewPager(
            tabList = tabViewDataList
        ) { tabViewSelectedIndex ->
            when (tabViewDataList[tabViewSelectedIndex]) {
                MatchEvents -> {
                    MatchEvents(
                        dataModel = dataModel,
                    )
                }
                Lineup -> {
                    dataModel.lineups?.let { lineups -> LineupsView(lineups) }
                }
                Statistics -> {
                    StatisticsView(
                        statistics = dataModel.statistics,
                        headToHead = dataModel.headToHead,
                        isTabVisible = tabViewSelectedIndex == Statistics.id,
                        zoneId = zoneId,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FixtureDetailsPreview() {
    WhosPlayingTheme {
        FixtureDetailsView(
            dataModel = detailsScreenPreviewDataModel,
            tabViewDataList = listOf(
                MatchEvents,
                Statistics
            )
        )
    }
}