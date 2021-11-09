package com.diegopizzo.whosplaying.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.smallPadding
import com.diegopizzo.whosplaying.ui.component.attr.tinyPadding
import com.diegopizzo.whosplaying.ui.component.common.*


@Composable
fun ComposeEvent(
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

@Composable
private fun EventIcon(@DrawableRes drawableRes: Int) {
    Image(
        painter = painterResource(id = drawableRes),
        contentDescription = ""
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