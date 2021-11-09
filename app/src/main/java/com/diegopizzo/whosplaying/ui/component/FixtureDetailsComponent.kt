package com.diegopizzo.whosplaying.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.diegopizzo.whosplaying.ui.component.attr.smallPadding
import com.diegopizzo.whosplaying.ui.component.attr.tinyPadding
import com.diegopizzo.whosplaying.ui.component.common.DefaultText
import com.diegopizzo.whosplaying.ui.component.common.LargeText
import com.diegopizzo.whosplaying.ui.component.common.MyCard
import com.diegopizzo.whosplaying.ui.component.common.SmallText

@Composable
fun ComposeFixturesDetails(
    logoHomeTeam: String,
    homeTeam: String,
    scoreHomeTeam: String,
    logoAwayTeam: String,
    awayTeam: String,
    scoreAwayTeam: String,
    status: String,
    onClick: (() -> Unit)? = null
) {
    MyCard(content = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(smallPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ComposeImage(logoHomeTeam)
                DefaultText(homeTeam)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ComposeMatchScore(
                    scoreHomeTeam,
                    scoreAwayTeam,
                    status
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ComposeImage(logoAwayTeam)
                DefaultText(awayTeam)
            }
        }
    }, onClick)
}

@Composable
private fun ComposeImage(logoUrl: String) {
    Image(
        painter = rememberImagePainter(
            data = logoUrl,
            builder = {
                placeholder(android.R.drawable.star_on)
            }
        ),
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}

@Composable
private fun ComposeMatchScore(scoreHomeTeam: String?, scoreAwayTeam: String?, status: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            LargeText(text = scoreHomeTeam ?: "0", modifier = Modifier.padding(tinyPadding))
            LargeText(text = ":", modifier = Modifier.padding(tinyPadding))
            LargeText(text = scoreAwayTeam ?: "0", modifier = Modifier.padding(tinyPadding))
        }
        Row {
            SmallText(text = status)
        }
    }
}


@Preview
@Composable
private fun FixturesDetailsPreview() {
    ComposeFixturesDetails(
        "",
        "AC Milan",
        "3",
        "",
        "Fc Inter",
        "0",
        "Match Finished"
    )
}