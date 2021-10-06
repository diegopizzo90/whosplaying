package com.diegopizzo.whosplaying.database.repository.league

interface ILeagueRepository {
    suspend fun downloadLeaguesInfo()
}