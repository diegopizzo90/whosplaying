package com.diegopizzo.database.dao

import androidx.room.*
import com.diegopizzo.database.entity.LeagueEntity

@Dao
interface LeagueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLeagues(vararg league: LeagueEntity)

    @Query("SELECT * FROM league")
    fun getAll(): List<LeagueEntity>?

    @Query("SELECT * FROM league WHERE league.name = :leagueName")
    fun getLeagueByName(leagueName: String): LeagueEntity?
}