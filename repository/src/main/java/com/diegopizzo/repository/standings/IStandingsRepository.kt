package com.diegopizzo.repository.standings

import com.diegopizzo.network.interactor.league.LeagueName
import com.diegopizzo.network.model.StandingsDataModel

interface IStandingsRepository {
    suspend fun getStandingsByLeague(leagueName: LeagueName): List<StandingsDataModel>
}