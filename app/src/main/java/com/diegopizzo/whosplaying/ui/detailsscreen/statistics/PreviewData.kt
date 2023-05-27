package com.diegopizzo.whosplaying.ui.detailsscreen.statistics

import com.diegopizzo.network.model.EventStatistics
import com.diegopizzo.network.model.EventStatistics.StatisticsType.*
import com.diegopizzo.network.model.HeadToHeadDataModel

object PreviewData {
    val statisticsModel = listOf(
        EventStatistics(
            497,
            504,
            SHOTS_ON_GOAL,
            "3",
            "3",
            0.5f,
            0.5f
        ),
        EventStatistics(
            497,
            504,
            SHOTS_OFF_GOAL,
            "3",
            "0",
            1.0f,
            0.0f
        ),
        EventStatistics(
            497,
            504,
            TOTAL_SHOTS,
            "7",
            "5",
            0.59f,
            0.42f
        ),
        EventStatistics(
            497,
            504,
            BLOCKED_SHOTS,
            "1",
            "2",
            0.34f,
            0.67f
        ),
        EventStatistics(
            497,
            504,
            SHOTS_INSIDE_BOX,
            "5",
            "3",
            0.63f,
            0.38f
        ),
        EventStatistics(
            497,
            504,
            SHOTS_OUTSIDE_BOX,
            "2",
            "2",
            .5f,
            .5f
        ),
        EventStatistics(
            497,
            504,
            FOULS,
            "18",
            "14",
            0.57f,
            0.44f
        ),
        EventStatistics(
            497,
            504,
            CORNER_KICKS,
            "4",
            "2",
            0.67f,
            0.34f
        ),
        EventStatistics(
            497,
            504,
            OFFSIDES,
            "1",
            "3",
            0.25f,
            0.75f
        ),
        EventStatistics(
            497,
            504,
            BALL_POSSESSION,
            "58%",
            "42%",
            0.58f,
            0.42f
        ),
        EventStatistics(
            497,
            504,
            YELLOW_CARDS,
            "3",
            "2",
            0.61f,
            0.41f
        ),
        EventStatistics(
            497,
            504,
            RED_CARDS,
            "0",
            "0",
            0.0f,
            0.0f
        ),
        EventStatistics(
            497,
            504,
            GOALKEEPER_SAVES,
            "1",
            "1",
            .5f,
            .5f
        ),
        EventStatistics(
            497,
            504,
            TOTAL_PASSES,
            "495",
            "375",
            0.57f,
            0.44f
        ),
        EventStatistics(
            497,
            504,
            PASSES_ACCURATE,
            "374",
            "272",
            0.58f,
            0.43f
        ),
        EventStatistics(
            497,
            504,
            PASSES,
            "76%",
            "73%",
            .76f,
            .73f
        )
    )

    val headToHead = listOf(
        HeadToHeadDataModel(
            "17/01/2021 12:13",
            "AC Milan",
            "",
            "3",
            "FC Inter",
            "",
            "0"
        ),
        HeadToHeadDataModel(
            "17/01/2019 14:20",
            "AC Milan",
            "",
            "3",
            "FC Inter",
            "",
            "0"
        ),
        HeadToHeadDataModel(
            "17/01/2016 14:30",
            "AC Milan",
            "",
            "3",
            "FC Inter",
            "",
            "0"
        )
    )
}