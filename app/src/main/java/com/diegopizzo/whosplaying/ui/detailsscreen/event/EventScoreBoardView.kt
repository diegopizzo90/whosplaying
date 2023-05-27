package com.diegopizzo.whosplaying.ui.detailsscreen.event

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme
import com.diegopizzo.whosplaying.ui.component.attr.smallPadding
import com.diegopizzo.whosplaying.ui.component.attr.tinyPadding
import com.diegopizzo.whosplaying.ui.component.common.*
import com.diegopizzo.whosplaying.ui.component.common.PainterViewData.Companion.urlPainter

@Composable
fun ComposeEventScoreBoard(
    logoHomeTeam: String,
    homeTeam: String,
    scoreHomeTeam: String,
    logoAwayTeam: String,
    awayTeam: String,
    scoreAwayTeam: String,
    status: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    MyCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(smallPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
            ) {
                AppIcon(
                    painter = urlPainter(url = logoHomeTeam),
                    modifier = Modifier.size(48.dp)
                )
                SmallText(homeTeam, maxLines = 2, textAlign = TextAlign.Center)
            }
            Spacer(modifier = Modifier.weight(1f))
            ComposeMatchScore(
                scoreHomeTeam = scoreHomeTeam,
                scoreAwayTeam = scoreAwayTeam,
                status = status,
                modifier = Modifier.weight(1.5f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
            ) {
                AppIcon(
                    painter = urlPainter(url = logoAwayTeam),
                    modifier = Modifier.size(48.dp)
                )
                SmallText(awayTeam, maxLines = 2, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
private fun ComposeMatchScore(
    scoreHomeTeam: String?,
    scoreAwayTeam: String?,
    status: String,
    modifier: Modifier = Modifier,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Row {
            LargeText(text = scoreHomeTeam ?: "0", modifier = Modifier.padding(tinyPadding))
            LargeText(text = ":", modifier = Modifier.padding(tinyPadding))
            LargeText(text = scoreAwayTeam ?: "0", modifier = Modifier.padding(tinyPadding))
        }
        Row {
            TinyText(text = status)
        }
    }
}


@Preview
@Composable
private fun FixturesDetailsPreview() {
    WhosPlayingTheme {
        ComposeEventScoreBoard(
            "",
            "AC Milan",
            "3",
            "",
            "Fc Inter",
            "0",
            "Match Finished"
        )
    }
}