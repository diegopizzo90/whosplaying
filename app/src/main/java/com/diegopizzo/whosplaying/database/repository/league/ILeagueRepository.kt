package com.diegopizzo.whosplaying.database.repository.league

import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.LeagueInfo

interface ILeagueRepository {
    suspend fun getLeagueByName(leagueName: LeagueName): LeagueInfo?
}