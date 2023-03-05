package com.diegopizzo.network.cache.standings

import com.diegopizzo.network.model.StandingsModel
import retrofit2.Response

interface IStandingsInteractorCache {
    suspend fun getStandings(season: String, leagueId: String): Response<StandingsModel>
    suspend fun clearCache()
}
