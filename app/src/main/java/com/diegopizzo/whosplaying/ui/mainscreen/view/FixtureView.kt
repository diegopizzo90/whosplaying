package com.diegopizzo.whosplaying.ui.mainscreen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.diegopizzo.network.model.FixtureDataModel
import com.diegopizzo.whosplaying.R.string
import com.diegopizzo.whosplaying.ui.blinkingcircle.BlinkingCircleView
import com.diegopizzo.whosplaying.ui.component.attr.*
import com.diegopizzo.whosplaying.ui.component.common.*
import com.diegopizzo.whosplaying.ui.component.common.PainterViewData.Companion.urlPainter
import com.diegopizzo.whosplaying.ui.mainscreen.ScreenResult
import com.google.android.material.color.MaterialColors
import com.valentinilk.shimmer.shimmer

private val DividerColor = teal700.copy(alpha = MaterialColors.ALPHA_LOW)

@Composable
fun FixtureView(
    modifier: Modifier = Modifier,
    state: ScreenResult?,
    viewData: List<FixtureDataModel>,
    onFixtureClicked: (id: Long) -> Unit = {}
) {

    when (state) {
        ScreenResult.ShowErrorResult -> NoEventsView(modifier)
        ScreenResult.ShowProgressBar -> LoadingView(modifier)
        ScreenResult.ShowSuccessResult -> {
            FixtureContent(
                viewData = viewData,
                modifier = modifier,
                onClick = onFixtureClicked
            )
        }
        else -> Unit
    }
}

@Composable
private fun FixtureContent(
    viewData: List<FixtureDataModel>,
    modifier: Modifier = Modifier,
    onClick: (id: Long) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.background(MaterialTheme.colors.background),
    ) {
        items(viewData) { fixture ->
            fixture.run {
                MyCard(
                    modifier = modifier,
                    onClick = { onClick(fixture.fixtureId) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .drawBehind {
                                drawLine(
                                    color = DividerColor,
                                    start = Offset(0f, 0f),
                                    end = Offset(size.width, 0f),
                                    strokeWidth = 2.dp.toPx()
                                )
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(tinyPadding)
                                .weight(1f),
                            verticalArrangement = Arrangement.spacedBy(smallPadding),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            if (isFixtureLive) BlinkingCircleView()
                            MediumText(text = status)
                        }
                        VerticalDivider()
                        Column(
                            modifier = Modifier
                                .padding(defaultPadding)
                                .weight(4f),
                            verticalArrangement = Arrangement.spacedBy(smallPadding),
                        ) {
                            TeamContent(logoTeamUrl = logoHomeTeam, nameTeam = homeTeam)
                            TeamContent(logoTeamUrl = logoAwayTeam, nameTeam = awayTeam)
                        }
                        Spacer(Modifier.weight(1f))
                        VerticalDivider()
                        Column(
                            modifier = Modifier
                                .padding(defaultPadding)
                                .weight(1f),
                            verticalArrangement = Arrangement.spacedBy(smallPadding),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            DefaultText(text = goalHomeTeam)
                            DefaultText(text = goalAwayTeam)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TeamContent(logoTeamUrl: String, nameTeam: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(defaultPadding)
    ) {
        AppIcon(
            painter = urlPainter(logoTeamUrl),
            modifier = Modifier.size(24.dp)
        )
        BodyText(text = nameTeam)
    }
}

@Composable
private fun NoEventsView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        DefaultText(
            text = stringResource(id = string.no_events_available),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun LoadingView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.background(MaterialTheme.colors.background),
    ) {
        repeat(10) {
            MyCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .drawBehind {
                            drawLine(
                                color = DividerColor,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = 2.dp.toPx()
                            )
                        }
                ) {
                    LoadingContent(
                        height = 40.dp,
                        modifier = Modifier
                            .padding(defaultPadding)
                            .align(Alignment.CenterVertically)
                    )
                    VerticalDivider()
                    Column(
                        modifier = Modifier.padding(defaultPadding),
                        verticalArrangement = Arrangement.spacedBy(defaultPadding),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(defaultPadding),
                        ) {
                            LoadingContent()
                            LoadingContent(width = 150.dp)
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(defaultPadding),
                        ) {
                            LoadingContent()
                            LoadingContent(width = 150.dp)
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    VerticalDivider()
                    Column(
                        modifier = Modifier
                            .padding(defaultPadding)
                            .align(Alignment.CenterVertically),
                        verticalArrangement = Arrangement.spacedBy(defaultPadding),
                    ) {
                        LoadingContent(width = 20.dp, height = 20.dp)
                        LoadingContent(width = 20.dp, height = 20.dp)
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier,
    width: Dp = 24.dp,
    height: Dp = 24.dp,
) {
    Box(
        modifier = modifier
            .shimmer()
            .width(width)
            .height(height)
            .background(lightGrey)
    )
}

@Composable
private fun VerticalDivider() {
    MyDivider(
        modifier = Modifier
            .width(1.dp)
            .fillMaxHeight(),
        color = DividerColor
    )
}

@Composable
@Preview
private fun FixtureViewPreview() {
    WhosPlayingTheme {
        FixtureContent(viewData = fixtures)
    }
}

@Composable
@Preview
private fun NoEventsViewPreview() {
    WhosPlayingTheme {
        NoEventsView()
    }
}

@Composable
@Preview
private fun LoadingContentPreview() {
    WhosPlayingTheme {
        LoadingView()
    }
}