package com.diegopizzo.whosplaying.ui.detailsscreen.lineups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.network.model.LineupsDataModel
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.blueDark2
import com.diegopizzo.whosplaying.ui.component.attr.blueDark3
import com.diegopizzo.whosplaying.ui.component.attr.smallPadding
import com.diegopizzo.whosplaying.ui.component.common.MyDivider
import com.diegopizzo.whosplaying.ui.component.common.SmallText

@Composable
private fun LineupItem(
    numberHomeTeam: String? = null,
    nameHomeTeam: String,
    numberAwayTeam: String? = null,
    nameAwayTeam: String,
    backgroundColor: Color = blueDark3,
    fontWeight: FontWeight? = null,
    isDividerVisible: Boolean = true
) {
    Row(
        Modifier
            .background(backgroundColor)
            .fillMaxWidth()
    ) {
        Column {
            Row {
                Row(Modifier.fillMaxWidth(.5f)) {
                    numberHomeTeam?.let { SmallText(it, Modifier.padding(start = smallPadding)) }
                    SmallText(
                        nameHomeTeam,
                        Modifier.padding(start = smallPadding),
                        fontWeight = fontWeight
                    )
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    SmallText(
                        nameAwayTeam,
                        Modifier.padding(end = smallPadding),
                        fontWeight = fontWeight
                    )
                    numberAwayTeam?.let { SmallText(it, Modifier.padding(end = smallPadding)) }
                }
            }
            if (isDividerVisible) MyDivider()
        }
    }
}

@Composable
private fun LineupTitleItem(nameHome: String, nameAway: String) {
    LineupItem(
        nameHomeTeam = nameHome,
        nameAwayTeam = nameAway,
        backgroundColor = blueDark2,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun LineupRowDivider() {
    LineupItem(
        nameHomeTeam = "",
        nameAwayTeam = "",
        backgroundColor = blueDark2,
        fontWeight = FontWeight.Bold,
        isDividerVisible = false
    )
}

@Composable
fun LineupsView(lineups: LineupsDataModel) {
    val home = lineups.homeTeamLineup
    val away = lineups.awayTeamLineup

    LazyColumn(content = {
        item {
            LineupTitleItem(stringResource(R.string.coach), stringResource(R.string.coach))
            LineupItem(
                nameHomeTeam = home.coachName,
                nameAwayTeam = away.coachName
            )
            LineupRowDivider()
            LineupTitleItem(
                stringResource(R.string.starting_eleven),
                stringResource(R.string.starting_eleven)
            )
        }
        items(home.startEleven.zip(away.startEleven)) {
            val homeXI = it.first
            val awayXI = it.second
            LineupItem(homeXI.number, homeXI.name, awayXI.number, awayXI.name)
        }
        item {
            LineupRowDivider()
            LineupTitleItem(
                stringResource(R.string.substitutes),
                stringResource(R.string.substitutes)
            )
        }
        items(home.substitutions.zip(away.substitutions)) {
            val homeSubs = it.first
            val awaySubs = it.second
            LineupItem(homeSubs.number, homeSubs.name, awaySubs.number, awaySubs.name)
        }
    })
}

@Preview
@Composable
fun LineupTitleItemPreview() {
    LineupTitleItem("Coach", "Coach")
}

@Preview
@Composable
fun LineupItemPreview() {
    LineupItem(
        numberHomeTeam = "11",
        nameHomeTeam = "Z. Ibrahimovic",
        numberAwayTeam = "9",
        nameAwayTeam = "T. Abraham"
    )
}

@Preview
@Composable
fun LineupsViewPreview() {
    val lineup = LineupsDataModel(
        LineupsDataModel.TeamLineup(
            497,
            "AS Roma",
            "José Mourinho",
            "3-4-1-2",
            listOf(
                LineupsDataModel.PlayerDataModel(2674, "Rui Patrício", "1", "G"),
                LineupsDataModel.PlayerDataModel(892, "C. Smalling", "6", "D"),
                LineupsDataModel.PlayerDataModel(770, "R. Karsdorp", "2", "D"),
                LineupsDataModel.PlayerDataModel(30924, "M. Kumbulla", "24", "D"),
                LineupsDataModel.PlayerDataModel(51572, "M. Viña", "5", "M"),
                LineupsDataModel.PlayerDataModel(2375, "Sérgio Oliveira", "27", "M"),
                LineupsDataModel.PlayerDataModel(778, "B. Cristante", "4", "M"),
                LineupsDataModel.PlayerDataModel(1456, "A. Maitland-Niles", "15", "M"),
                LineupsDataModel.PlayerDataModel(782, "L. Pellegrini", "7", "F"),
                LineupsDataModel.PlayerDataModel(19194, "T. Abraham", "9", "F"),
                LineupsDataModel.PlayerDataModel(342038, "F. Afena-Gyan", "64", "F")
            ),
            listOf(
                LineupsDataModel.PlayerDataModel(30409, "J. Veretout", "17", "M"),
                LineupsDataModel.PlayerDataModel(203474, "N. Zalewski", "59", "F"),
                LineupsDataModel.PlayerDataModel(342035, "C. Volpato", "62", "M"),
                LineupsDataModel.PlayerDataModel(286475, "E. Bove", "52", "M"),
                LineupsDataModel.PlayerDataModel(763, "Daniel Fuzato", "87", "G"),
                LineupsDataModel.PlayerDataModel(342653, "F. Missori", "58", "D"),
                LineupsDataModel.PlayerDataModel(342071, "D. Mastrantonio", "67", "G"),
                LineupsDataModel.PlayerDataModel(324, "A. Diawara", "42", "M"),
                LineupsDataModel.PlayerDataModel(343385, "D. Keramitsis", "75", "D"),
                LineupsDataModel.PlayerDataModel(158059, "E. Darboe", "55", "M")
            )
        ), LineupsDataModel.TeamLineup(
            497,
            "AS Roma",
            "José Mourinho",
            "3-4-1-2",
            listOf(
                LineupsDataModel.PlayerDataModel(2674, "Rui Patrício", "1", "G"),
                LineupsDataModel.PlayerDataModel(892, "C. Smalling", "6", "D"),
                LineupsDataModel.PlayerDataModel(770, "R. Karsdorp", "2", "D"),
                LineupsDataModel.PlayerDataModel(30924, "M. Kumbulla", "24", "D"),
                LineupsDataModel.PlayerDataModel(51572, "M. Viña", "5", "M"),
                LineupsDataModel.PlayerDataModel(2375, "Sérgio Oliveira", "27", "M"),
                LineupsDataModel.PlayerDataModel(778, "B. Cristante", "4", "M"),
                LineupsDataModel.PlayerDataModel(1456, "A. Maitland-Niles", "15", "M"),
                LineupsDataModel.PlayerDataModel(782, "L. Pellegrini", "7", "F"),
                LineupsDataModel.PlayerDataModel(19194, "T. Abraham", "9", "F"),
                LineupsDataModel.PlayerDataModel(342038, "F. Afena-Gyan", "64", "F")
            ),
            listOf(
                LineupsDataModel.PlayerDataModel(30409, "J. Veretout", "17", "M"),
                LineupsDataModel.PlayerDataModel(203474, "N. Zalewski", "59", "F"),
                LineupsDataModel.PlayerDataModel(342035, "C. Volpato", "62", "M"),
                LineupsDataModel.PlayerDataModel(286475, "E. Bove", "52", "M"),
                LineupsDataModel.PlayerDataModel(763, "Daniel Fuzato", "87", "G"),
                LineupsDataModel.PlayerDataModel(342653, "F. Missori", "58", "D"),
                LineupsDataModel.PlayerDataModel(342071, "D. Mastrantonio", "67", "G"),
                LineupsDataModel.PlayerDataModel(324, "A. Diawara", "42", "M"),
                LineupsDataModel.PlayerDataModel(343385, "D. Keramitsis", "75", "D"),
                LineupsDataModel.PlayerDataModel(158059, "E. Darboe", "55", "M")
            )
        )
    )
    LineupsView(lineup)
}