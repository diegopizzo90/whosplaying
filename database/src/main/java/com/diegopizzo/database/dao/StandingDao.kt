package com.diegopizzo.database.dao

import androidx.room.*
import com.diegopizzo.database.entity.StandingsEntity

@Dao
interface StandingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStandings(vararg standings: StandingsEntity)

    @Transaction
    @Query("SELECT * FROM standings WHERE standingsLeagueId = :leagueId")
    fun getStandingsByLeagueId(leagueId: Long): List<StandingsEntity>?

    @Transaction
    @Query("DELETE FROM standings WHERE standingsLeagueId = :leagueId")
    fun deleteAllByLeagueId(leagueId: Long)
}