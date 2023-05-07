package com.diegopizzo.whosplaying.ui.mainscreen.view.bottomnavigation

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.whosplaying.ui.component.attr.blueDark
import com.diegopizzo.whosplaying.ui.component.attr.blueDark3
import com.diegopizzo.whosplaying.ui.component.attr.teal700
import com.diegopizzo.whosplaying.ui.component.attr.white
import com.diegopizzo.whosplaying.ui.mainscreen.view.bottomnavigation.BottomNavScreen.*

@Composable
fun BottomNavigationView(
    bottomNavItems: List<BottomNavScreen>,
    leagueSelected: LeagueName,
    modifier: Modifier = Modifier,
    onItemClicked: (navItem: BottomNavScreen) -> Unit = {}
) {
    BottomNavigation(
        modifier = modifier.drawBehind {
            drawLine(
                color = teal700,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 2.dp.toPx()
            )
        },
        backgroundColor = blueDark,
        contentColor = white,
    ) {
        bottomNavItems.forEach { navItem ->
            BottomNavigationItem(
                selected = leagueSelected == navItem.id,
                onClick = {
                    onItemClicked(navItem)
                },
                icon = {
                    Icon(
                        painter = painterResource(navItem.itemIcon),
                        contentDescription = stringResource(navItem.itemName),
                        tint = white
                    )
                },
                modifier = if (leagueSelected == navItem.id) Modifier.background(blueDark3) else Modifier,
                label = {
                    Text(
                        text = stringResource(navItem.itemName),
                        fontSize = 10.sp,
                        color = white,
                    )
                },
            )
        }
    }
}

@Preview
@Composable
private fun BottomNavigationViewPreview() {
    BottomNavigationView(
        bottomNavItems = listOf(
            SerieA,
            PremierLeague,
            LaLiga,
            Bundesliga,
            Ligue1,
        ),
        leagueSelected = SerieA.id,
    )
}