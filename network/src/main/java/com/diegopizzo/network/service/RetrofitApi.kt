package com.diegopizzo.network.service

import com.diegopizzo.network.model.*
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
    suspend fun getStandings(
        @Query("season") season: String,
        @Query("league") leagueId: String
    ): Response<StandingsModel>

    @GET("fixtures/statistics")
    suspend fun getStatistics(@Query("fixture") fixtureId: Long): Response<StatisticsModel>

    @GET("fixtures/lineups")
    suspend fun getLineups(@Query("fixture") fixtureId: Long): Response<LineupsModel>
}