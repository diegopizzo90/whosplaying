package com.diegopizzo.repository.league

import com.diegopizzo.network.interactor.league.LeagueName

interface ILeagueRepository {
    suspend fun downloadLeaguesInfo()
    suspend fun getLeagueId(leagueName: LeagueName): Long?
}