package com.diegopizzo.whosplaying.ui.mainscreen.navigation

const val LEAGUE_NAME_KEY = "leagueName"
const val FIXTURE_ID_KEY = "fixtureId"

sealed class Destination(val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    object HomeScreen : NoArgsDestination(route = "home")

    object StandingsScreen : Destination(route = "standings", LEAGUE_NAME_KEY) {
        operator fun invoke(leagueName: String): String = route.appendParams(
            LEAGUE_NAME_KEY to leagueName,
        )
    }

    object DetailsScreen : Destination(route = "details", FIXTURE_ID_KEY) {
        operator fun invoke(fixtureId: String): String = route.appendParams(
            FIXTURE_ID_KEY to fixtureId,
        )
    }

    internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
        val builder = StringBuilder(this)

        params.forEach {
            it.second?.toString()?.let { arg ->
                builder.append("/$arg")
            }
        }

        return builder.toString()
    }
}