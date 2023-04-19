package com.diegopizzo.whosplaying.ui.mainscreen.navigation


sealed class Destination(val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    object HomeScreen : NoArgsDestination("home")

    object StandingsScreen : NoArgsDestination("standings")

    object DetailsScreen : NoArgsDestination("details")
}