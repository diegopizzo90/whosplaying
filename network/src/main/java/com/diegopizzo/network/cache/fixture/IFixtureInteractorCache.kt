package com.diegopizzo.network.cache.fixture

import com.diegopizzo.network.model.FixtureModel
import retrofit2.Response

interface IFixtureInteractorCache {

    suspend fun getFixturesByLeagueIdAndByDate(
        leagueId: String,
        season: String,
        from: String,
        to: String
    ): Response<FixtureModel>

    suspend fun clearCache()
}
