package com.diegopizzo.whosplaying.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.diegopizzo.whosplaying.ui.component.attr.smallPadding
import com.diegopizzo.whosplaying.ui.component.attr.tinyPadding
import com.diegopizzo.whosplaying.ui.component.common.LargeText
import com.diegopizzo.whosplaying.ui.component.common.MyCard
import com.diegopizzo.whosplaying.ui.component.common.SmallText
import com.diegopizzo.whosplaying.ui.component.common.TinyText

@Composable
fun ComposeFixturesDetails(
    logoHomeTeam: String,
    homeTeam: String,
    scoreHomeTeam: String,
    logoAwayTeam: String,
    awayTeam: String,
    scoreAwayTeam: String,
    status: String,
    onClick: (() -> Unit)? = null,
) {
    MyCard(content = {
        ConstraintLayout {
            val (homeColumn, scoreView, awayColumn) = createRefs()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.constrainAs(homeColumn) {
                    start.linkTo(parent.start, margin = smallPadding)
                    top.linkTo(parent.top, margin = smallPadding)
                    bottom.linkTo(parent.bottom, margin = smallPadding)
                }) {

                ComposeImage(logoHomeTeam)
                SmallText(homeTeam)
            }

            ComposeMatchScore(scoreHomeTeam, scoreAwayTeam, status,
                modifier = Modifier
                    .constrainAs(scoreView) {
                        top.linkTo(parent.top, margin = smallPadding)
                        bottom.linkTo(parent.bottom, margin = smallPadding)
                    }
                    .then(Modifier.fillMaxWidth()))

            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.constrainAs(awayColumn) {
                    end.linkTo(parent.end, margin = smallPadding)
                    top.linkTo(parent.top, margin = smallPadding)
                    bottom.linkTo(parent.bottom, margin = smallPadding)
                }) {
                ComposeImage(logoAwayTeam)
                SmallText(awayTeam)
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
        modifier = Modifier.size(48.dp)
    )
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