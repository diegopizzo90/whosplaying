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
import com.diegopizzo.whosplaying.ui.mainscreen.ViewEffect
import com.valentinilk.shimmer.shimmer

@Composable
fun FixtureView(
    modifier: Modifier = Modifier,
    state: ViewEffect,
    viewData: List<FixtureDataModel>
) {
    when (state) {
        ViewEffect.ShowErrorResult -> NoEventsView(modifier)
        ViewEffect.ShowProgressBar -> LoadingView(modifier)
        ViewEffect.ShowSuccessResult -> {
            FixtureContent(
                viewData = viewData, modifier = modifier
            )
        }
    }
}

@Composable
private fun FixtureContent(
    viewData: List<FixtureDataModel>,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    LazyColumn(modifier = modifier) {
        items(viewData) { fixture ->
            fixture.run {
                MyCard(
                    modifier = modifier,
                    onClick = onClick
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .drawBehind {
                                drawLine(
                                    color = teal700,
                                    start = Offset(0f, 0f),
                                    end = Offset(size.width, 0f),
                                    strokeWidth = 2.dp.toPx()
                                )
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier.padding(smallPadding),
                            verticalArrangement = Arrangement.spacedBy(smallPadding),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            if (isFixtureLive) BlinkingCircleView()
                            MediumText(text = status)
                        }
                        VerticalDivider()
                        Column(
                            modifier = Modifier.padding(defaultPadding),
                            verticalArrangement = Arrangement.spacedBy(smallPadding),
                        ) {
                            TeamContent(logoTeamUrl = logoHomeTeam, nameTeam = homeTeam)
                            TeamContent(logoTeamUrl = logoAwayTeam, nameTeam = awayTeam)
                        }
                        Spacer(Modifier.weight(1f))
                        VerticalDivider()
                        Column(
                            modifier = Modifier.padding(defaultPadding),
                            verticalArrangement = Arrangement.spacedBy(smallPadding),
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
        ComposeImage(logoUrl = logoTeamUrl)
        DefaultText(text = nameTeam)
    }
}

@Composable
private fun NoEventsView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backgroundColor),
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
        modifier = modifier,
    ) {
        repeat(10) {
            MyCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .background(blueDark)
                        .drawBehind {
                            drawLine(
                                color = teal700,
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
private fun LoadingContent(width: Dp = 24.dp, height: Dp = 24.dp, modifier: Modifier = Modifier) {
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
            .fillMaxHeight()
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