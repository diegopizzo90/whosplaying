package com.diegopizzo.whosplaying.ui.detailsscreen.statistics

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.diegopizzo.network.model.EventStatistics
import com.diegopizzo.network.model.EventStatistics.StatisticsType.*
import com.diegopizzo.network.model.HeadToHeadDataModel
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.*
import com.diegopizzo.whosplaying.ui.component.common.MediumText
import com.diegopizzo.whosplaying.ui.component.common.MyDivider
import com.diegopizzo.whosplaying.ui.detailsscreen.statistics.PreviewData.headToHead
import com.diegopizzo.whosplaying.ui.detailsscreen.statistics.PreviewData.statisticsModel
import org.threeten.bp.ZoneId

@Composable
private fun MatchStatisticsItem(
    modifier: Modifier = Modifier,
    homeValue: String,
    awayValue: String,
    homeProgressValue: Float,
    awayProgressValue: Float,
    homeColor: Color = Color.White,
    awayColor: Color = teal700,
    statisticName: String,
    isTabVisible: Boolean,
) {
    ConstraintLayout(
        modifier.then(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
        )
    ) {
        val (homeTxt, progressBarRtl, progressBarLtl, awayTxt, statistic, divider) = createRefs()

        MediumText(homeValue, modifier = Modifier.constrainAs(homeTxt) {
            start.linkTo(parent.start, smallPadding)
            end.linkTo(progressBarRtl.start, defaultPadding)
            bottom.linkTo(parent.bottom)
        })

        val guideline = createGuidelineFromTop(.5f)

        MediumText(statisticName, modifier = Modifier.constrainAs(statistic) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(guideline)
        })

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            MyLinearProgressIndicator(
                indicatorProgress = homeProgressValue,
                color = homeColor,
                modifier = Modifier
                    .constrainAs(progressBarRtl) {
                        start.linkTo(homeTxt.end)
                        end.linkTo(progressBarLtl.start)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        centerVerticallyTo(homeTxt)
                    },
                hasProgressStarted = isTabVisible,
            )
        }
        MyLinearProgressIndicator(
            indicatorProgress = awayProgressValue,
            color = awayColor,
            modifier = Modifier
                .constrainAs(progressBarLtl) {
                    start.linkTo(progressBarRtl.end)
                    end.linkTo(awayTxt.start)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    centerVerticallyTo(awayTxt)
                },
            hasProgressStarted = isTabVisible,
        )
        MediumText(awayValue, modifier = Modifier.constrainAs(awayTxt) {
            start.linkTo(progressBarLtl.end, defaultPadding)
            end.linkTo(parent.end, smallPadding)
            bottom.linkTo(parent.bottom)
        })
        MyDivider(Modifier.constrainAs(divider) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        })
    }
}

@Composable
private fun MyLinearProgressIndicator(
    indicatorProgress: Float,
    color: Color,
    modifier: Modifier = Modifier,
    hasProgressStarted: Boolean,
) {
    val durationMillis = 2000
    val progress = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(Unit) {
        if (hasProgressStarted) {
            progress.animateTo(
                targetValue = indicatorProgress,
                animationSpec = tween(
                    easing = LinearOutSlowInEasing,
                    durationMillis = durationMillis,
                )
            )
        }
    }

    LinearProgressIndicator(
        modifier = modifier,
        progress = progress.value,
        backgroundColor = Color.Transparent,
        color = color
    )
}

@Composable
private fun StatisticsTitleItem(title: String) {
    Column(
        Modifier
            .background(blueDark2)
            .fillMaxWidth()
    ) {
        MediumText(
            title,
            Modifier.padding(horizontal = smallPadding, vertical = defaultPadding),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun getStatisticName(type: EventStatistics.StatisticsType): String {
    return when (type) {
        SHOTS_ON_GOAL -> stringResource(R.string.shots_on_goal)
        SHOTS_OFF_GOAL -> stringResource(R.string.shots_off_goal)
        TOTAL_SHOTS -> stringResource(R.string.total_shots)
        BLOCKED_SHOTS -> stringResource(R.string.blocked_shots)
        SHOTS_INSIDE_BOX -> stringResource(R.string.shots_inside_box)
        SHOTS_OUTSIDE_BOX -> stringResource(R.string.shots_outside_box)
        FOULS -> stringResource(R.string.fouls)
        CORNER_KICKS -> stringResource(R.string.corner_kicks)
        OFFSIDES -> stringResource(R.string.offsides)
        BALL_POSSESSION -> stringResource(R.string.ball_possession)
        YELLOW_CARDS -> stringResource(R.string.yellow_cards)
        RED_CARDS -> stringResource(R.string.red_cards)
        GOALKEEPER_SAVES -> stringResource(R.string.goalkeeper_saves)
        TOTAL_PASSES -> stringResource(R.string.total_passes)
        PASSES_ACCURATE -> stringResource(R.string.passes_accurate)
        PASSES -> stringResource(R.string.passes_percentage)
        EXPECTED_GOALS -> stringResource(R.string.expected_goals)
    }
}

@Composable
fun StatisticsView(
    statistics: List<EventStatistics>,
    headToHead: List<HeadToHeadDataModel>,
    isTabVisible: Boolean,
    zoneId: ZoneId? = null,
) {
    val verticalScrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(verticalScrollState)
    ) {
        statistics.forEach {
            val statisticName = it.type?.let { name -> getStatisticName(name) } ?: ""
            MatchStatisticsItem(
                homeValue = it.valueTeamHome,
                awayValue = it.valueTeamAway,
                statisticName = statisticName,
                homeProgressValue = it.percentageValueTeamHome,
                awayProgressValue = it.percentageValueTeamAway,
                isTabVisible = isTabVisible,
            )
        }
    }
    if (headToHead.isNotEmpty()) {
        StatisticsTitleItem(stringResource(R.string.head_to_head))
    }
    headToHead.forEach {
        HeadToHeadItem(
            dateUtc = it.date,
            nameHomeTeam = it.nameHome,
            logoHomeTeam = it.logoHome,
            scoreHomeTeam = it.scoreHomeTeam,
            nameAwayTeam = it.nameAway,
            logoAwayTeam = it.logoAway,
            scoreAwayTeam = it.scoreAwayTeam,
            zoneId = zoneId,
        )
    }
}

@Preview
@Composable
fun MyLinearProgressIndicatorPreview() {
    WhosPlayingTheme {
        MyLinearProgressIndicator(
            indicatorProgress = 5f,
            color = Color.White,
            hasProgressStarted = true,
        )
    }
}

@Preview
@Composable
fun StatisticsItemPreview() {
    WhosPlayingTheme {
        MatchStatisticsItem(
            homeValue = "5",
            awayValue = "8",
            homeProgressValue = .4f,
            awayProgressValue = .7f,
            statisticName = "Corner",
            isTabVisible = true,
        )
    }
}

@Preview
@Composable
fun StatisticsViewPreview() {
    WhosPlayingTheme {
        StatisticsView(
            statistics = statisticsModel,
            headToHead = headToHead,
            isTabVisible = true,
        )
    }
}