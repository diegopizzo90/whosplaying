package com.diegopizzo.network.service

import com.diegopizzo.network.model.*
import ir.logicbase.mockfit.Mock
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @Mock("league_by_country.json", includeQueries = ["name=Serie A", "code=IT"])
    @GET("leagues")
    suspend fun getLeagueByCountry(
        @Query("name") leagueName: String,
        @Query("code") alpha2Code: String
    ): Response<LeagueModel>

    @Mock("fixtures_by_leagueId_by_date.json")
    @GET("fixtures")
    suspend fun getFixturesByLeagueIdAndByDate(
        @Query("league") leagueId: String,
        @Query("season") season: String,
        @Query("from") from: String,
        @Query("to") to: String
    ): Response<FixtureModel>

    @Mock("fixtures_by_id.json")
    @GET("fixtures")
    suspend fun getEventByFixtureId(@Query("id") fixtureId: Long): Response<EventModel>

    @Mock("standings.json")
    @GET("standings")
    suspend fun getStandings(
        @Query("season") season: String,
        @Query("league") leagueId: String
    ): Response<StandingsModel>

    @Mock("statistics.json")
    @GET("fixtures/statistics")
    suspend fun getStatistics(@Query("fixture") fixtureId: Long): Response<StatisticsModel>

    @Mock("lineups.json")
    @GET("fixtures/lineups")
    suspend fun getLineups(@Query("fixture") fixtureId: Long): Response<LineupsModel>

    @Mock("headtohead.json")
    @GET("fixtures/headtohead")
    suspend fun getHeadToHead(
        @Query("h2h") fixtureIds: String,
        @Query("last") maxResults: Int = 8
    ): Response<HeadToHeadModel>
}