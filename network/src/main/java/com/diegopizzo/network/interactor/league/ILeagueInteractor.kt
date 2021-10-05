package com.diegopizzo.network.interactor.league

import com.diegopizzo.network.model.LeagueInfo

interface ILeagueInteractor {
    suspend fun getLeague(leagueName: LeagueName): LeagueInfo?
}