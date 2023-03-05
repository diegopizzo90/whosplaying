package com.diegopizzo.network.interactor.league

import com.diegopizzo.network.model.LeagueResponse

interface ILeagueInteractor {
    suspend fun getLeague(leagueName: LeagueName): LeagueResponse?
}