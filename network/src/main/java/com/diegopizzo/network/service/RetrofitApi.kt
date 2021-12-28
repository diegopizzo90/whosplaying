package com.diegopizzo.network.service

import com.diegopizzo.network.model.EventModel
import com.diegopizzo.network.model.FixtureModel
import com.diegopizzo.network.model.LeagueModel
import com.diegopizzo.network.model.StandingModel
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

    @GET("fixtures")
    suspend fun getEventByFixtureId(@Query("id") fixtureId: Long): Response<EventModel>

    @GET("standings")
    suspend fun getStanding(
        @Query("season") season: String,
        @Query("league") leagueId: String
    ): Response<StandingModel>
}