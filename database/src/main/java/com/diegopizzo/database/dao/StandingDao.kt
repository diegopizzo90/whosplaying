package com.diegopizzo.database.dao

import androidx.room.*
import com.diegopizzo.database.entity.StandingEntity

@Dao
interface StandingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStandings(vararg standing: StandingEntity)

    @Transaction
    @Query("SELECT * FROM standing WHERE standingLeagueId = :leagueId")
    fun getStandingsByLeagueId(leagueId: Long): List<StandingEntity>?

    @Transaction
    @Query("DELETE FROM standing WHERE standingLeagueId = :leagueId")
    fun deleteAllByLeagueId(leagueId: Long)
}