package com.diegopizzo.whosplaying.ui.detailsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.network.model.*
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.ComposeEvent
import com.diegopizzo.whosplaying.ui.component.ComposeFixturesDetails
import com.diegopizzo.whosplaying.ui.component.attr.smallPadding
import com.diegopizzo.whosplaying.ui.component.common.MediumText
import com.diegopizzo.whosplaying.ui.component.common.MyCard

@Composable
fun ComposeDetailsView(dataModel: EventDataModel) {
    Column {
        ComposeFixturesDetails(
            dataModel.logoHomeTeam,
            dataModel.homeTeam,
            dataModel.scoreHomeTeam,
            dataModel.logoAwayTeam,
            dataModel.awayTeam,
            dataModel.scoreAwayTeam,
            dataModel.status.long
        )
        MyCard(content = {
            Column {
                MediumText(
                    text = stringResource(R.string.match_events),
                    modifier = Modifier.padding(smallPadding)
                )
                LazyColumn {
                    items(
                        items = dataModel.events,
                        itemContent = {
                            val eventPair = getEventDrawableAndDetail(it.type, it.detail)
                            ComposeEvent(
                                it.elapsedEvent,
                                eventPair.first,
                                it.mainPlayer,
                                it.secondPlayer,
                                eventPair.second,
                                isHomeTeamEvent(dataModel.homeTeamId, it.idTeamEvent)
                            )
                        }
                    )
                }
            }
        })
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
    )
)