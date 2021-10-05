package com.diegopizzo.whosplaying.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diegopizzo.whosplaying.database.entity.LeagueEntity

@Dao
interface LeagueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg league: LeagueEntity)

    @Query("SELECT * FROM league")
    fun getAll(): List<LeagueEntity>?

    @Query("SELECT * FROM league WHERE league.name = :leagueName")
    fun getLeagueByName(leagueName: String): LeagueEntity?
}