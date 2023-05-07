package com.diegopizzo.whosplaying.ui.standings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.StandingsDataModel
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.row
import com.diegopizzo.whosplaying.ui.component.attr.teal700
import com.diegopizzo.whosplaying.ui.component.attr.tinyPadding
import com.diegopizzo.whosplaying.ui.component.common.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun StandingsContent(
    leagueName: LeagueName?,
    viewModel: StandingsViewModel = koinViewModel(),
) {
    val viewDataState = viewModel.viewStates().observeAsState().value ?: return

    LaunchedEffect(key1 = leagueName) {
        leagueName?.let { viewModel.getStandings(it) }
    }

    if (viewDataState.isLoading) {
        LoadingView()
    } else {
        MyScaffold(
            navigationOnClick = {
                viewModel.onBackClicked()
            },
        ) {
            StandingsView(
                modifier = Modifier.padding(it),
                standings = viewDataState.standings,
            )
        }
    }

}

@Composable
private fun StandingsView(
    modifier: Modifier = Modifier,
    standings: List<StandingsDataModel>,
) {
    LazyColumn(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)

    ) {
        item {
            Row {
                Column(Modifier.fillMaxWidth(.5F)) {
                    StandingsItemLeftSide(firstItemModel(), FontWeight.Bold)
                    repeat(standings.size) {
                        StandingsItemLeftSide(item = standings[it])
                    }
                }
                LazyRow {
                    item {
                        Column {
                            StandingsItemRightSide(firstItemModel(), FontWeight.Bold)
                            repeat(standings.size) {
                                StandingsItemRightSide(item = standings[it])
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StandingsItemCell(
    text: String,
    modifier: Modifier = Modifier,
    logo: String = "",
    fontWeight: FontWeight? = null,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center
) {
    Row(
        modifier = modifier.then(Modifier.height(24.dp)),
        horizontalArrangement = horizontalArrangement
    ) {
        if (logo.isNotEmpty()) {
            ComposeImage(
                logo,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = tinyPadding)
            )
        }
        SmallText(
            text = text,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun StandingsItemLeftSide(item: StandingsDataModel, fontWeight: FontWeight? = null) {
    ConstraintLayout(Modifier.background(MaterialTheme.colors.row)) {
        val (rank, verticalDivider, name, verticalDivider2, horizontalDivider) = createRefs()
        StandingsItemCell(
            item.rank,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(rank) {
                    start.linkTo(parent.start, margin = tinyPadding)
                    end.linkTo(verticalDivider.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(.15F)
                }
        )
        VerticalDivider(Modifier.constrainAs(verticalDivider) {
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
                    start.linkTo(verticalDivider.end)
                    end.linkTo(verticalDivider2.start, margin = tinyPadding)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(.75F)
                },
            horizontalArrangement = Arrangement.Start
        )
        VerticalDivider(Modifier.constrainAs(verticalDivider2) {
            start.linkTo(name.end)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
        })

        MyDivider(color = teal700, thickness = 2.dp, modifier = Modifier
            .constrainAs(horizontalDivider) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(rank.bottom)
            })
    }
}

@Composable
private fun StandingsItemRightSide(item: StandingsDataModel, fontWeight: FontWeight? = null) {
    ConstraintLayout(
        Modifier
            .background(MaterialTheme.colors.row)
            .fillMaxWidth()
    ) {
        val (points, played, win, draw, lose, scored, against, goalsDiff, form, horizontalDivider) = createRefs()
        StandingsItemCell(
            item.points,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(points) {
                    start.linkTo(parent.start, margin = tinyPadding)
                    end.linkTo(played.start, margin = tinyPadding)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(.1F)
                }
        )

        StandingsItemCell(
            item.played,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(played) {
                    start.linkTo(points.end)
                    end.linkTo(win.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(.08F)
                }
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
                    width = Dimension.percent(.08F)
                }
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
                    width = Dimension.percent(.06F)
                }
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
                    width = Dimension.percent(.08F)
                }
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
                    width = Dimension.percent(.08F)
                }
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
                    width = Dimension.percent(.08F)
                }
        )

        StandingsItemCell(
            item.goalsDiff,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(goalsDiff) {
                    start.linkTo(against.end, margin = tinyPadding)
                    end.linkTo(form.start, margin = tinyPadding)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(.08F)
                }
        )

        StandingsItemCell(
            item.form,
            fontWeight = fontWeight,
            modifier = Modifier
                .constrainAs(form) {
                    start.linkTo(goalsDiff.end, margin = tinyPadding)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.percent(.22F)
                }
        )

        MyDivider(color = teal700, thickness = 2.dp, modifier = Modifier
            .constrainAs(horizontalDivider) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(points.bottom)
                width = Dimension.fillToConstraints
            })
    }
}

@Composable
private fun firstItemModel() = StandingsDataModel(
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
    stringResource(R.string.against)
)

@Preview
@Composable
private fun StandingsPreview() {
    StandingsView(standings = standingsMock)
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