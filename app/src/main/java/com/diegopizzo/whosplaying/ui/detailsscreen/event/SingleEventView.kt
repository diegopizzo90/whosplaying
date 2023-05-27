package com.diegopizzo.whosplaying.ui.detailsscreen.event

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.diegopizzo.network.model.EventDataModel
import com.diegopizzo.network.model.EventType
import com.diegopizzo.network.model.EventTypeDetail
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.smallPadding
import com.diegopizzo.whosplaying.ui.component.attr.tinyPadding
import com.diegopizzo.whosplaying.ui.component.common.*


@Composable
fun MatchEvents(
    dataModel: EventDataModel,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
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

@Composable
private fun ComposeEvent(
    elapsedEvent: String,
    @DrawableRes drawableRes: Int,
    mainPlayer: String,
    secondPlayer: String?,
    eventDetails: String?,
    isHomeEvent: Boolean,
) {
    if (isHomeEvent) {
        ComposeHomeEvent(elapsedEvent, drawableRes, mainPlayer, secondPlayer, eventDetails)
    } else {
        ComposeAwayEvent(elapsedEvent, drawableRes, mainPlayer, secondPlayer, eventDetails)
    }
}

@Composable
private fun ComposeHomeEvent(
    elapsedEvent: String,
    @DrawableRes drawableRes: Int,
    mainPlayer: String,
    secondPlayer: String?,
    eventDetails: String?,
) {
    MyRow(horizontalArrangement = Arrangement.Start,
        content = {
            TinySpacer()
            EventColumn {
                SmallText(text = elapsedEvent)
            }
            TinySpacer()
            EventColumn {
                EventIcon(drawableRes)
            }
            TinySpacer()
            EventColumn {
                SmallText(text = mainPlayer, fontWeight = FontWeight.Bold)
                secondPlayer?.let { TinyText(text = it) }
                eventDetails?.let {
                    SuperTinyText(
                        text = "(${it})",
                        Modifier.padding(top = smallPadding)
                    )
                }
            }
        })
}

@Composable
private fun ComposeAwayEvent(
    elapsedEvent: String,
    @DrawableRes drawableRes: Int,
    mainPlayer: String,
    secondPlayer: String?,
    eventDetails: String?,
) {
    MyRow(horizontalArrangement = Arrangement.End,
        content = {
            EventColumn {
                SmallText(text = mainPlayer, fontWeight = FontWeight.Bold)
                secondPlayer?.let { TinyText(text = it) }
                eventDetails?.let {
                    SuperTinyText(
                        text = "(${it})",
                        Modifier.padding(top = smallPadding)
                    )
                }
            }
            TinySpacer()
            EventColumn {
                EventIcon(drawableRes)
            }
            TinySpacer()
            EventColumn {
                SmallText(text = elapsedEvent)
            }
            TinySpacer()
        })
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

@Composable
private fun EventIcon(@DrawableRes drawableRes: Int) {
    Image(
        painter = rememberAsyncImagePainter(drawableRes),
        contentDescription = null,
        modifier = Modifier.size(24.dp)
    )
}

@Composable
private fun EventColumn(columnContent: @Composable () -> Unit) {
    Column(
        modifier = Modifier.padding(
            horizontal = tinyPadding,
            vertical = tinyPadding
        )
    ) { columnContent.invoke() }
}

@Preview
@Composable
private fun EventPreview1() {
    ComposeAwayEvent(
        elapsedEvent = "23′",
        drawableRes = R.drawable.ic_yellow_card,
        mainPlayer = "Krunic",
        secondPlayer = null,
        eventDetails = null
    )
}

@Preview
@Composable
private fun EventPreview2() {
    ComposeHomeEvent(
        elapsedEvent = "45′",
        drawableRes = R.drawable.ic_goal,
        mainPlayer = "Ibrahimovic",
        secondPlayer = "R. Leao",
        eventDetails = "Penalty"
    )
}

@Preview
@Composable
private fun EventPreview3() {
    ComposeAwayEvent(
        elapsedEvent = "45′",
        drawableRes = R.drawable.ic_goal,
        mainPlayer = "Ibrahimovic",
        secondPlayer = "R. Leao",
        eventDetails = null
    )
}