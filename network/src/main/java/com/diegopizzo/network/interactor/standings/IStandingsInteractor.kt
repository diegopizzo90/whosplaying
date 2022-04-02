package com.diegopizzo.network.interactor.standings

import com.diegopizzo.network.model.StandingsDataModel

interface IStandingsInteractor {
    suspend fun getStandingsByLeagueId(leagueId: String): List<StandingsDataModel>
    suspend fun clearCache()
}
