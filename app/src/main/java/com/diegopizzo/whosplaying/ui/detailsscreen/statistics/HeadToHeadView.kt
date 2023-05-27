package com.diegopizzo.whosplaying.ui.detailsscreen.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.diegopizzo.network.CommonConstant.DATE_TIME_PATTERN
import com.diegopizzo.network.Util
import com.diegopizzo.whosplaying.ui.component.attr.blueDark3
import com.diegopizzo.whosplaying.ui.component.attr.smallPadding
import com.diegopizzo.whosplaying.ui.component.attr.tinyPadding
import com.diegopizzo.whosplaying.ui.component.common.DefaultText
import com.diegopizzo.whosplaying.ui.component.common.MyDivider
import com.diegopizzo.whosplaying.ui.component.common.SmallText
import com.diegopizzo.whosplaying.ui.component.common.TinyText
import org.threeten.bp.ZoneId

@Composable
private fun HeadToHeadTeamColumn(nameHomeTeam: String, logoHomeTeam: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(150.dp)
    ) {
        AsyncImage(model = logoHomeTeam, contentDescription = null, modifier = Modifier.size(20.dp))
        SmallText(nameHomeTeam, Modifier.padding(top = tinyPadding))
    }
}

@Composable
fun HeadToHeadItem(
    dateUtc: String,
    nameHomeTeam: String,
    logoHomeTeam: String,
    scoreHomeTeam: String,
    nameAwayTeam: String,
    logoAwayTeam: String,
    scoreAwayTeam: String,
    zoneId: ZoneId? = null
) {
    Column(
        Modifier
            .background(blueDark3)
            .padding(top = tinyPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val dateLocal = zoneId?.let {
            Util.convertUtcDateTimeToLocal(
                dateUtc,
                it,
                DATE_TIME_PATTERN
            )
        }
        dateLocal?.let { TinyText(it) }
        Row(
            Modifier
                .height(IntrinsicSize.Min)
                .padding(horizontal = tinyPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeadToHeadTeamColumn(nameHomeTeam, logoHomeTeam)
            Row(
                modifier = Modifier.fillMaxHeight(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DefaultText(text = scoreHomeTeam, Modifier.padding(end = smallPadding))
                DefaultText(text = ":")
                DefaultText(text = scoreAwayTeam, Modifier.padding(start = smallPadding))
            }
            HeadToHeadTeamColumn(nameAwayTeam, logoAwayTeam)
        }
        MyDivider(Modifier.padding(top = tinyPadding))
    }
}

@Composable
@Preview
private fun HeadToHeadItemPreview() {
    HeadToHeadItem(
        dateUtc = "17/01/1990 13:20",
        nameHomeTeam = "AC Milan",
        logoHomeTeam = "",
        scoreHomeTeam = "3",
        nameAwayTeam = "FC Inter",
        logoAwayTeam = "",
        scoreAwayTeam = "0"
    )
}