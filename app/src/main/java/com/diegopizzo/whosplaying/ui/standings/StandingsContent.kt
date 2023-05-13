package com.diegopizzo.whosplaying.ui.standings

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.StandingsDataModel
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme
import com.diegopizzo.whosplaying.ui.component.attr.teal700
import com.diegopizzo.whosplaying.ui.component.attr.tinyPadding
import com.diegopizzo.whosplaying.ui.component.common.*
import com.diegopizzo.whosplaying.ui.component.common.PainterViewData.Companion.urlPainter
import com.diegopizzo.whosplaying.ui.standings.StandingsScreenResult.ShowProgressBar
import com.diegopizzo.whosplaying.ui.standings.StandingsScreenResult.ShowSuccessResult
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

    when (viewDataState.screenResult) {
        ShowProgressBar -> LoadingView()
        ShowSuccessResult -> {
            MyScaffold(
                navigationOnClick = {
                    viewModel.onBackClicked()
                },
            ) {
                StandingsView(
                    standings = viewDataState.standings,
                    modifier = Modifier.padding(it),
                )
            }
        }
        else -> Unit
    }
}

@Composable
private fun StandingsView(
    standings: List<StandingsDataModel>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(.5f)
        ) {
            StandingsLeftSide(
                item = firstItemModel(),
                fontWeight = FontWeight.Bold,
                horizontalDividerThickness = 2.dp,
            )
            repeat(standings.size) {
                StandingsLeftSide(
                    item = standings[it],
                    horizontalArrangement = Arrangement.Start,
                )
            }
        }
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .requiredWidth(IntrinsicSize.Min)
        ) {
            Column {
                StandingsRightSide(
                    item = firstItemModel(),
                    fontWeight = FontWeight.Bold,
                    horizontalDividerThickness = 2.dp,
                )
                repeat(standings.size) {
                    StandingsRightSide(
                        item = standings[it],
                    )
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
        modifier = modifier
            .defaultMinSize(minHeight = 40.dp),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (logo.isNotEmpty()) {
            AppIcon(
                painter = urlPainter(logo),
                modifier = Modifier
                    .padding(end = tinyPadding)
                    .size(24.dp)
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
private fun StandingsLeftSide(
    item: StandingsDataModel,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = null,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    horizontalDividerThickness: Dp = 1.dp,
) {
    Column(modifier = modifier.background(MaterialTheme.colors.surface)) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(tinyPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StandingsItemCell(
                text = item.rank,
                modifier = Modifier
                    .padding(start = tinyPadding)
                    .weight(.5f),
                fontWeight = fontWeight,
                horizontalArrangement = Arrangement.Center,
            )
            VerticalDivider(Modifier.fillMaxHeight())
            StandingsItemCell(
                text = item.nameTeam,
                logo = item.logoTeam,
                fontWeight = fontWeight,
                modifier = Modifier.weight(2f),
                horizontalArrangement = horizontalArrangement,
            )
            VerticalDivider(Modifier.fillMaxHeight())
        }
        MyDivider(color = teal700, thickness = horizontalDividerThickness)
    }
}

@Composable
private fun StandingsRightSide(
    item: StandingsDataModel,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = null,
    horizontalDividerThickness: Dp = 1.dp,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.surface)
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(tinyPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StandingsItemCell(
                text = item.points,
                fontWeight = fontWeight,
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(Modifier.fillMaxHeight())
            StandingsItemCell(
                item.played,
                fontWeight = fontWeight,
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(Modifier.fillMaxHeight())
            StandingsItemCell(
                item.win,
                fontWeight = fontWeight,
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(Modifier.fillMaxHeight())
            StandingsItemCell(
                item.draw,
                fontWeight = fontWeight,
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(Modifier.fillMaxHeight())
            StandingsItemCell(
                item.lose,
                fontWeight = fontWeight,
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(Modifier.fillMaxHeight())
            StandingsItemCell(
                item.scored,
                fontWeight = fontWeight,
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(Modifier.fillMaxHeight())
            StandingsItemCell(
                item.against,
                fontWeight = fontWeight,
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(Modifier.fillMaxHeight())
            StandingsItemCell(
                item.goalsDiff,
                fontWeight = fontWeight,
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(Modifier.fillMaxHeight())
            StandingsItemCell(
                item.form,
                fontWeight = fontWeight,
                modifier = Modifier
                    .weight(2f)
                    .padding(end = tinyPadding),
            )
        }
        MyDivider(color = teal700, thickness = horizontalDividerThickness)
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
    WhosPlayingTheme {
        StandingsView(standings = standingsMock)
    }
}

val standingsMock = listOf(
    StandingsDataModel(
        "505",
        "AC Milan",
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
        "Inter",
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