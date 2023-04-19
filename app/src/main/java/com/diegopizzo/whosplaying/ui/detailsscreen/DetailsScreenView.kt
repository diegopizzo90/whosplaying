@file:OptIn(ExperimentalPagerApi::class)

package com.diegopizzo.whosplaying.ui.detailsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.network.model.EventDataModel
import com.diegopizzo.network.model.EventType
import com.diegopizzo.network.model.EventTypeDetail
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.common.LoadingView
import com.diegopizzo.whosplaying.ui.component.common.MyScaffold
import com.diegopizzo.whosplaying.ui.component.viewpager.TabViewPager
import com.diegopizzo.whosplaying.ui.detailsscreen.event.ComposeEvent
import com.diegopizzo.whosplaying.ui.detailsscreen.event.ComposeEventScoreBoard
import com.diegopizzo.whosplaying.ui.detailsscreen.lineups.LineupsView
import com.diegopizzo.whosplaying.ui.detailsscreen.statistics.StatisticsView
import com.google.accompanist.pager.ExperimentalPagerApi

@Composable
fun FixtureDetailsContent(
    viewModel: IDetailsScreenViewModel,
    onBackClicked: () -> Unit = {},
) {
    val viewDataState = viewModel.viewStates().observeAsState().value ?: return

    if (viewDataState.isLoading) {
        LoadingView()
    } else {
        MyScaffold(
            navigationOnClick = {
                onBackClicked()
            },
        ) {
            FixtureDetailsView(
                modifier = Modifier.padding(it),
                dataModel = viewDataState.eventDataModel
            )
        }
    }
}

@Composable
private fun FixtureDetailsView(
    modifier: Modifier = Modifier,
    dataModel: EventDataModel
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
            tabList = listOf(
                stringResource(R.string.match_events),
                stringResource(R.string.statistics),
                stringResource(R.string.lineups)
            )
        ) {
            when (it) {
                stringResource(R.string.match_events) -> {
                    LazyColumn {
                        items(items = dataModel.events, itemContent = { event ->
                            val eventPair = getEventDrawableAndDetail(event.type, event.detail)
                            ComposeEvent(
                                event.elapsedEvent, eventPair.first,
                                event.mainPlayer, event.secondPlayer, eventPair.second,
                                isHomeTeamEvent(dataModel.homeTeamId, event.idTeamEvent)
                            )
                        })
                    }
                }
                stringResource(R.string.statistics) -> {
                    StatisticsView(dataModel.statistics, dataModel.headToHead)
                }
                stringResource(R.string.lineups) -> {
                    dataModel.lineups?.let { lineups -> LineupsView(lineups) }
                }
            }
        }
    }
}

private fun getEventDrawableAndDetail(
    eventType: EventType,
    eventTypeDetail: EventTypeDetail,
): Pair<Int, String?> {
    return when (eventType) {
        EventType.GOAL -> {
            when (eventTypeDetail) {
                EventTypeDetail.MISSED_PENALTY -> Pair(R.drawable.ic_penalty_missed, null)
                EventTypeDetail.NORMAL_GOAL -> Pair(R.drawable.ic_goal, null)
                else -> Pair(R.drawable.ic_goal, eventTypeDetail.detail)
            }
        }
        EventType.SUBSTITUTION -> Pair(R.drawable.ic_substitution, null)
        EventType.CARD -> {
            when (eventTypeDetail) {
                EventTypeDetail.YELLOW_CARD -> Pair(R.drawable.ic_yellow_card, null)
                EventTypeDetail.RED_CARD -> Pair(R.drawable.ic_red_card, null)
                else -> Pair(R.drawable.ic_second_yellow_card, null)
            }
        }
        EventType.VAR -> Pair(R.drawable.ic_var, eventTypeDetail.detail)
    }
}

private fun isHomeTeamEvent(homeId: Long, eventTeamId: Long): Boolean {
    return homeId == eventTeamId
}

@Preview
@Composable
private fun FixtureDetailsView() {
    FixtureDetailsView(dataModel = detailsScreenPreviewDataModel)
}