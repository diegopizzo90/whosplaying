package com.diegopizzo.whosplaying.database.dao

import androidx.room.*
import com.diegopizzo.whosplaying.database.entity.FixtureEntity

@Dao
interface FixtureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFixture(vararg fixture: FixtureEntity)

    @Query("SELECT * FROM fixture")
    fun getAll(): List<FixtureEntity>?

    @Transaction
    @Query("SELECT * FROM fixture WHERE fixtureLeagueId = :leagueId")
    fun getFixturesByLeagueId(leagueId: Long): List<FixtureEntity>?

    @Transaction
    @Query("DELETE FROM fixture WHERE fixtureLeagueId = :leagueId")
    fun deleteAllByLeagueId(leagueId: Long)
}