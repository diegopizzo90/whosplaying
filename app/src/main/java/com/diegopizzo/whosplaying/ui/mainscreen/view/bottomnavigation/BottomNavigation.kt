package com.diegopizzo.whosplaying.ui.mainscreen.view.bottomnavigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.whosplaying.R

sealed class BottomNavScreen(
    @StringRes val itemName: Int,
    val id: LeagueName,
    @DrawableRes val itemIcon: Int
) {
    object SerieA : BottomNavScreen(
        itemName = R.string.serie_a,
        id = LeagueName.SERIE_A,
        itemIcon = R.drawable.ic_serie_a,
    )

    object PremierLeague : BottomNavScreen(
        itemName = R.string.premier_league,
        id = LeagueName.PREMIER_LEAGUE,
        itemIcon = R.drawable.ic_premier_league,
    )

    object LaLiga : BottomNavScreen(
        itemName = R.string.la_liga,
        id = LeagueName.LA_LIGA,
        itemIcon = R.drawable.ic_la_liga,
    )

    object Bundesliga : BottomNavScreen(
        itemName = R.string.bundesliga,
        id = LeagueName.BUNDESLIGA,
        itemIcon = R.drawable.ic_bundesliga,
    )

    object Ligue1 : BottomNavScreen(
        itemName = R.string.ligue_1,
        id = LeagueName.LIGUE_1,
        itemIcon = R.drawable.ic_ligue_1,
    )
}