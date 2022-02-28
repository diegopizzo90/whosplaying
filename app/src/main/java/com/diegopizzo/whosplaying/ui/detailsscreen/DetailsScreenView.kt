package com.diegopizzo.whosplaying.ui.detailsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.network.model.*
import com.diegopizzo.network.model.EventStatistics.StatisticsType.*
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.viewpager.TabViewPager
import com.diegopizzo.whosplaying.ui.detailsscreen.event.ComposeEvent
import com.diegopizzo.whosplaying.ui.detailsscreen.event.ComposeEventScoreBoard
import com.diegopizzo.whosplaying.ui.detailsscreen.lineups.LineupsView
import com.diegopizzo.whosplaying.ui.detailsscreen.statistics.StatisticsView
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun ComposeDetailsView(dataModel: EventDataModel) {
    Column {
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
                    StatisticsView(dataModel.statistics)
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

@ExperimentalPagerApi
@Preview
@Composable
private fun ComposeDetailsView() {
    ComposeDetailsView(dataModel)
}

val dataModel = EventDataModel(
    1,
    "2021-10-02T18:45:00+00:00",
    StatusValue.MATCH_FINISHED,
    "90",
    517,
    "AC Milan",
    518,
    "FC Inter",
    "",
    "",
    "3",
    "0",
    listOf(
        SingleEvent(
            "26′",
            517,
            "R. Leao",
            "A. Rebic",
            EventType.SUBSTITUTION,
            EventTypeDetail.SUBSTITUTION_1
        ),
        SingleEvent(
            "36′",
            518,
            "Barella",
            null,
            EventType.CARD,
            EventTypeDetail.YELLOW_CARD
        ),
        SingleEvent(
            "38′",
            517,
            "Z. Ibrahimovic",
            "A. Rebic",
            EventType.GOAL,
            EventTypeDetail.NORMAL_GOAL
        ),
        SingleEvent(
            "41′",
            518,
            "L. Martinez",
            null,
            EventType.GOAL,
            EventTypeDetail.MISSED_PENALTY
        ),
        SingleEvent(
            "52′",
            517,
            "Z. Ibrahimovic",
            null,
            EventType.GOAL,
            EventTypeDetail.NORMAL_GOAL
        ),
        SingleEvent(
            "52′",
            517,
            "Krunic",
            null,
            EventType.CARD,
            EventTypeDetail.YELLOW_CARD
        ),
        SingleEvent(
            "63′",
            518,
            "Barella",
            null,
            EventType.CARD,
            EventTypeDetail.SECOND_YELLOW_CARD
        ),
        SingleEvent(
            "63′",
            517,
            "Z. Ibrahimovic",
            null,
            EventType.VAR,
            EventTypeDetail.PENALTY_CONFIRMED
        ),
        SingleEvent(
            "65′",
            517,
            "Z. Ibrahimovic",
            "Tomori",
            EventType.GOAL,
            EventTypeDetail.PENALTY
        ),
        SingleEvent(
            "90′+2′",
            518,
            "H. Çalhanoğlu",
            null,
            EventType.CARD,
            EventTypeDetail.RED_CARD
        )
    ), statistics = listOf(
        EventStatistics(497, 504, SHOTS_ON_GOAL, "3", "3", 0.5f, 0.5f),
        EventStatistics(497, 504, SHOTS_OFF_GOAL, "3", "0", 1.0f, 0.0f),
        EventStatistics(497, 504, TOTAL_SHOTS, "7", "5", 0.59f, 0.42f),
        EventStatistics(497, 504, BLOCKED_SHOTS, "1", "2", 0.34f, 0.67f),
        EventStatistics(497, 504, SHOTS_INSIDE_BOX, "5", "3", 0.63f, 0.38f),
        EventStatistics(497, 504, SHOTS_OUTSIDE_BOX, "2", "2", .5f, .5f),
        EventStatistics(497, 504, FOULS, "18", "14", 0.57f, 0.44f),
        EventStatistics(497, 504, CORNER_KICKS, "4", "2", 0.67f, 0.34f),
        EventStatistics(497, 504, OFFSIDES, "1", "3", 0.25f, 0.75f),
        EventStatistics(497, 504, BALL_POSSESSION, "58%", "42%", 0.58f, 0.42f),
        EventStatistics(497, 504, YELLOW_CARDS, "3", "2", 0.61f, 0.41f),
        EventStatistics(497, 504, RED_CARDS, "0", "0", 0.0f, 0.0f),
        EventStatistics(497, 504, GOALKEEPER_SAVES, "1", "1", .5f, .5f),
        EventStatistics(497, 504, TOTAL_PASSES, "495", "375", 0.57f, 0.44f),
        EventStatistics(497, 504, PASSES_ACCURATE, "374", "272", 0.58f, 0.43f),
        EventStatistics(497, 504, PASSES, "76%", "73%", .76f, .73f)
    ), lineups = LineupsDataModel(
        LineupsDataModel.TeamLineup(
            497,
            "AS Roma",
            "José Mourinho",
            "3-4-1-2",
            listOf(
                LineupsDataModel.PlayerDataModel(2674, "Rui Patrício", "1", "G"),
                LineupsDataModel.PlayerDataModel(892, "C. Smalling", "6", "D"),
                LineupsDataModel.PlayerDataModel(770, "R. Karsdorp", "2", "D"),
                LineupsDataModel.PlayerDataModel(30924, "M. Kumbulla", "24", "D"),
                LineupsDataModel.PlayerDataModel(51572, "M. Viña", "5", "M"),
                LineupsDataModel.PlayerDataModel(2375, "Sérgio Oliveira", "27", "M"),
                LineupsDataModel.PlayerDataModel(778, "B. Cristante", "4", "M"),
                LineupsDataModel.PlayerDataModel(1456, "A. Maitland-Niles", "15", "M"),
                LineupsDataModel.PlayerDataModel(782, "L. Pellegrini", "7", "F"),
                LineupsDataModel.PlayerDataModel(19194, "T. Abraham", "9", "F"),
                LineupsDataModel.PlayerDataModel(342038, "F. Afena-Gyan", "64", "F")
            ),
            listOf(
                LineupsDataModel.PlayerDataModel(30409, "J. Veretout", "17", "M"),
                LineupsDataModel.PlayerDataModel(203474, "N. Zalewski", "59", "F"),
                LineupsDataModel.PlayerDataModel(342035, "C. Volpato", "62", "M"),
                LineupsDataModel.PlayerDataModel(286475, "E. Bove", "52", "M"),
                LineupsDataModel.PlayerDataModel(763, "Daniel Fuzato", "87", "G"),
                LineupsDataModel.PlayerDataModel(342653, "F. Missori", "58", "D"),
                LineupsDataModel.PlayerDataModel(342071, "D. Mastrantonio", "67", "G"),
                LineupsDataModel.PlayerDataModel(324, "A. Diawara", "42", "M"),
                LineupsDataModel.PlayerDataModel(343385, "D. Keramitsis", "75", "D"),
                LineupsDataModel.PlayerDataModel(158059, "E. Darboe", "55", "M")
            )
        ), LineupsDataModel.TeamLineup(
            497,
            "AS Roma",
            "José Mourinho",
            "3-4-1-2",
            listOf(
                LineupsDataModel.PlayerDataModel(2674, "Rui Patrício", "1", "G"),
                LineupsDataModel.PlayerDataModel(892, "C. Smalling", "6", "D"),
                LineupsDataModel.PlayerDataModel(770, "R. Karsdorp", "2", "D"),
                LineupsDataModel.PlayerDataModel(30924, "M. Kumbulla", "24", "D"),
                LineupsDataModel.PlayerDataModel(51572, "M. Viña", "5", "M"),
                LineupsDataModel.PlayerDataModel(2375, "Sérgio Oliveira", "27", "M"),
                LineupsDataModel.PlayerDataModel(778, "B. Cristante", "4", "M"),
                LineupsDataModel.PlayerDataModel(1456, "A. Maitland-Niles", "15", "M"),
                LineupsDataModel.PlayerDataModel(782, "L. Pellegrini", "7", "F"),
                LineupsDataModel.PlayerDataModel(19194, "T. Abraham", "9", "F"),
                LineupsDataModel.PlayerDataModel(342038, "F. Afena-Gyan", "64", "F")
            ),
            listOf(
                LineupsDataModel.PlayerDataModel(30409, "J. Veretout", "17", "M"),
                LineupsDataModel.PlayerDataModel(203474, "N. Zalewski", "59", "F"),
                LineupsDataModel.PlayerDataModel(342035, "C. Volpato", "62", "M"),
                LineupsDataModel.PlayerDataModel(286475, "E. Bove", "52", "M"),
                LineupsDataModel.PlayerDataModel(763, "Daniel Fuzato", "87", "G"),
                LineupsDataModel.PlayerDataModel(342653, "F. Missori", "58", "D"),
                LineupsDataModel.PlayerDataModel(342071, "D. Mastrantonio", "67", "G"),
                LineupsDataModel.PlayerDataModel(324, "A. Diawara", "42", "M"),
                LineupsDataModel.PlayerDataModel(343385, "D. Keramitsis", "75", "D"),
                LineupsDataModel.PlayerDataModel(158059, "E. Darboe", "55", "M")
            )
        )
    )
)