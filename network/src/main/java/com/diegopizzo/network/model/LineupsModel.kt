package com.diegopizzo.network.model


data class TeamLineup(val id: Long, val name: String)
data class CoachLineup(val id: Long, val name: String)
data class PlayerLineup(
    val id: Long,
    val name: String,
    val number: String,
    val pos: String,
    val grid: String?
)
data class StartEleven(val player: PlayerLineup)
data class Substitutes(val player: PlayerLineup)
data class Lineup(
    val team: TeamLineup,
    val coach: CoachLineup,
    val formation: String,
    val startXI: List<StartEleven>,
    val substitutes: List<Substitutes>
)

data class LineupsModel(val response: List<Lineup>)