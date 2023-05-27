package com.diegopizzo.database.dao

import androidx.room.*
import com.diegopizzo.database.entity.LeagueEntity

@Dao
interface LeagueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLeagues(vararg league: LeagueEntity)

    @Query("SELECT * FROM league ORDER BY lastUpdate")
    fun getAll(): List<LeagueEntity>?

    @Query("SELECT * FROM league WHERE league.name = :leagueName")
    fun getLeagueByName(leagueName: String): LeagueEntity?

    @Query("SELECT * FROM league WHERE league.countryCode = :countryCode")
    fun getLeaguesByCountry(countryCode: String): List<LeagueEntity>?
}