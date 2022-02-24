package com.diegopizzo.whosplaying.ui.detailsscreen.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.diegopizzo.network.model.EventStatistics
import com.diegopizzo.network.model.EventStatistics.StatisticsType.*
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.defaultPadding
import com.diegopizzo.whosplaying.ui.component.attr.row
import com.diegopizzo.whosplaying.ui.component.attr.smallPadding
import com.diegopizzo.whosplaying.ui.component.attr.teal700
import com.diegopizzo.whosplaying.ui.component.common.MediumText
import com.diegopizzo.whosplaying.ui.component.common.MyDivider

@Composable
private fun StatisticsItem(
    modifier: Modifier = Modifier,
    homeValue: String,
    awayValue: String,
    homeProgressValue: Float,
    awayProgressValue: Float,
    homeColor: Color = Color.White,
    awayColor: Color = teal700,
    statisticName: String
) {
    ConstraintLayout(
        modifier.then(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.row)
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
            LinearProgressIndicator(
                progress = homeProgressValue,
                backgroundColor = Color.Transparent,
                color = homeColor,
                modifier = Modifier
                    .constrainAs(progressBarRtl) {
                        start.linkTo(homeTxt.end)
                        end.linkTo(progressBarLtl.start)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        centerVerticallyTo(homeTxt)
                    }
            )
        }
        LinearProgressIndicator(
            progress = awayProgressValue,
            backgroundColor = Color.Transparent,
            color = awayColor,
            modifier = Modifier
                .constrainAs(progressBarLtl) {
                    start.linkTo(progressBarRtl.end)
                    end.linkTo(awayTxt.start)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    centerVerticallyTo(awayTxt)
                }
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
    }
}

@Composable
fun StatisticsView(statistics: List<EventStatistics>) {
    LazyColumn {
        items(statistics) {
            val statisticName = it.type?.let { name -> getStatisticName(name) } ?: ""
            StatisticsItem(
                homeValue = it.valueTeamHome,
                awayValue = it.valueTeamAway,
                statisticName = statisticName,
                homeProgressValue = it.percentageValueTeamHome,
                awayProgressValue = it.percentageValueTeamAway
            )
        }
    }
}

@Preview
@Composable
fun StatisticsItemPreview() {
    StatisticsItem(
        homeValue = "5",
        awayValue = "8",
        homeProgressValue = .4f,
        awayProgressValue = .7f,
        statisticName = "Corner"
    )
}

@Preview
@Composable
fun StatisticsViewPreview() {
    val statisticsModel = listOf(
        EventStatistics(497, 504, SHOTS_ON_GOAL, "3", "3", 0.5f, 0.5f),
        EventStatistics(497, 504, SHOTS_OFF_GOAL, "3", "0", 1.0f, 0.0f),
        EventStatistics(497, 504, TOTAL_SHOTS, "7", "5", 0.59f, 0.42f),
        EventStatistics(497, 504, BLOCKED_SHOTS, "1", "2", 0.34f, 0.67f),
        EventStatistics(497, 504, SHOTS_INSIDE_BOX, "5", "3", 0.63f, 0.38f),
        EventStatistics(497, 504, SHOTS_OUTSIDE_BOX, "2", "2", .5f, .5f),
        EventStatistics(497, 504, FOULS, "18", "14", 0.57f, 0.44f),
        EventStatistics(497, 504, CORNER_KICKS, "4", "2", 0.67f, 0.34f),
        EventStatistics(497, 504, OFFSIDES, "1", "3", 0.25f, 0.75f),
        EventStatistics(497, 504, BALL_POSSESSION, "58%", "42%", 0.58f, 0.42f),
        EventStatistics(497, 504, YELLOW_CARDS, "3", "2", 0.61f, 0.41f),
        EventStatistics(497, 504, RED_CARDS, "0", "0", 0.0f, 0.0f),
        EventStatistics(497, 504, GOALKEEPER_SAVES, "1", "1", .5f, .5f),
        EventStatistics(497, 504, TOTAL_PASSES, "495", "375", 0.57f, 0.44f),
        EventStatistics(497, 504, PASSES_ACCURATE, "374", "272", 0.58f, 0.43f),
        EventStatistics(497, 504, PASSES, "76%", "73%", .76f, .73f)
    )
    StatisticsView(statisticsModel)
}