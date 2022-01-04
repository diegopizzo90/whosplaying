package com.diegopizzo.whosplaying.ui.standings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.diegopizzo.network.model.StandingsDataModel
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.backgroundColor
import com.diegopizzo.whosplaying.ui.component.attr.teal700
import com.diegopizzo.whosplaying.ui.component.attr.tinyPadding
import com.diegopizzo.whosplaying.ui.component.common.ComposeImage
import com.diegopizzo.whosplaying.ui.component.common.SmallText

@Composable
private fun StandingsItemCell(
    text: String,
    modifier: Modifier = Modifier,
    logo: String = "",
    fontWeight: FontWeight? = null
) {
    Row(modifier = modifier) {
        if (logo.isNotEmpty()) ComposeImage(
            logo,
            modifier = Modifier
                .size(20.dp)
                .padding(end = tinyPadding)
        )
        SmallText(
            text = text,
            fontWeight = fontWeight,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun VerticalDivider(modifier: Modifier) {
    Divider(color = teal700, modifier = modifier.then(Modifier.width(1.dp)))
}

@Composable
private fun StandingsItemRow(item: StandingsDataModel, fontWeight: FontWeight? = null) {
    ConstraintLayout {
        val (rank, divider1, name, divider2, points, played, win, draw, lose, scored, against, goalsDiff) = createRefs()
        StandingsItemCell(
            item.rank,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(rank) {
                    start.linkTo(parent.start, margin = tinyPadding)
                    end.linkTo(divider1.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(.01F)
        )
        VerticalDivider(Modifier.constrainAs(divider1) {
            start.linkTo(rank.end)
            end.linkTo(name.start, margin = tinyPadding)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
        })
        StandingsItemCell(
            item.nameTeam,
            logo = item.logoTeam,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(name) {
                    start.linkTo(divider1.end)
                    end.linkTo(points.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(.35F)
        )
        VerticalDivider(Modifier.constrainAs(divider2) {
            start.linkTo(name.end)
            end.linkTo(points.start, margin = tinyPadding)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
        })
        StandingsItemCell(
            item.points,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(points) {
                    start.linkTo(divider2.end, margin = tinyPadding)
                    end.linkTo(played.start, margin = tinyPadding)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(.06F)
        )

        StandingsItemCell(
            item.played,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(played) {
                    start.linkTo(points.end, margin = tinyPadding)
                    end.linkTo(win.start, margin = tinyPadding)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(.05F)
        )

        StandingsItemCell(
            item.win,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(win) {
                    start.linkTo(played.end, margin = tinyPadding)
                    end.linkTo(draw.start, margin = tinyPadding)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(.05F)
        )

        StandingsItemCell(
            item.draw,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(draw) {
                    start.linkTo(win.end, margin = tinyPadding)
                    end.linkTo(lose.start, margin = tinyPadding)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(.05F)
        )

        StandingsItemCell(
            item.lose,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(lose) {
                    start.linkTo(draw.end, margin = tinyPadding)
                    end.linkTo(scored.start, margin = tinyPadding)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(.05F)
        )

        StandingsItemCell(
            item.scored,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(scored) {
                    start.linkTo(lose.end, margin = tinyPadding)
                    end.linkTo(against.start, margin = tinyPadding)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(.05F)
        )

        StandingsItemCell(
            item.against,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(against) {
                    start.linkTo(scored.end, margin = tinyPadding)
                    end.linkTo(goalsDiff.start, margin = tinyPadding)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(.05F)
        )

        StandingsItemCell(
            item.goalsDiff,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(goalsDiff) {
                    start.linkTo(against.end, margin = tinyPadding)
                    end.linkTo(parent.end, margin = tinyPadding)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(.05F)
        )
    }
}

@Composable
private fun StandingsFirstRow() {
    val item = StandingsDataModel(
        "",
        stringResource(R.string.team),
        "",
        stringResource(R.string.rank),
        stringResource(R.string.points),
        stringResource(R.string.goals_difference),
        stringResource(R.string.form),
        stringResource(R.string.played),
        stringResource(R.string.win),
        stringResource(R.string.draw),
        stringResource(R.string.lose),
        stringResource(R.string.scored),
        stringResource(R.string.againsts)
    )
    StandingsItemRow(item, fontWeight = FontWeight.Bold)
}

@Composable
fun Standings(standings: List<StandingsDataModel>) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.backgroundColor)) {
        item {
            StandingsFirstRow()
            Divider(color = teal700, thickness = 2.dp)
        }
        items(
            items = standings,
            itemContent = {
                StandingsItemRow(item = it)
                Divider(color = teal700, thickness = 2.dp)
            }
        )
    }
}

@Preview
@Composable
fun StandingsItemColumnPreview() {
    StandingsItemCell("Pos", logo = "")
}

@Preview
@Composable
fun StandingsFirstRowPreview() {
    StandingsFirstRow()
}

@Preview
@Composable
fun StandingsPreview() {
    Standings(standingsMock)
}

val standingsMock = listOf(
    StandingsDataModel(
        "505",
        "Inter",
        "https://media.api-sports.io/football/teams/505.png",
        "1",
        "46",
        "34",
        "WWWWW",
        "19",
        "14",
        "4",
        "1",
        "49",
        "15"
    ),
    StandingsDataModel(
        "489",
        "AC Milan",
        "https://media.api-sports.io/football/teams/489.png",
        "2",
        "42",
        "18",
        "WLDWW",
        "19",
        "13",
        "3",
        "3",
        "40",
        "22"
    ),
    StandingsDataModel(
        "492",
        "Napoli",
        "https://media.api-sports.io/football/teams/492.png",
        "3",
        "39",
        "21",
        "LWLLD",
        "19",
        "12",
        "3",
        "4",
        "35",
        "14"
    )
)