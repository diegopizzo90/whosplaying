package com.diegopizzo.network.service

import com.diegopizzo.network.model.FixtureModel
import com.diegopizzo.network.model.LeagueModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("leagues")
    suspend fun getLeagueByCountry(
        @Query("name") leagueName: String,
        @Query("code") alpha2Code: String
    ): Response<LeagueModel>

    @GET("fixtures")
    suspend fun getFixturesByLeagueIdAndByDate(
        @Query("league") leagueId: String,
        @Query("season") season: String,
        @Query("from") from: String,
        @Query("to") to: String
    ): Response<FixtureModel>
}