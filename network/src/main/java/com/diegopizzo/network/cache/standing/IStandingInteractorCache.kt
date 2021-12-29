package com.diegopizzo.network.cache.standing

import com.diegopizzo.network.model.StandingModel
import retrofit2.Response

interface IStandingInteractorCache {
    suspend fun getStanding(season: String, leagueId: String): Response<StandingModel>
}
