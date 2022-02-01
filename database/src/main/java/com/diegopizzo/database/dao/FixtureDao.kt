package com.diegopizzo.database.dao

import androidx.room.*
import com.diegopizzo.database.entity.FixtureEntity
import org.threeten.bp.ZonedDateTime

@Dao
interface FixtureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFixture(vararg fixture: FixtureEntity)

    @Query("SELECT * FROM fixture")
    fun getAll(): List<FixtureEntity>?

    @Transaction
    @Query("SELECT * FROM fixture WHERE fixtureLeagueId = :leagueId AND dateTimeEvent BETWEEN :from AND :to")
    fun getFixturesByLeagueId(
        leagueId: Long,
        from: ZonedDateTime,
        to: ZonedDateTime
    ): List<FixtureEntity>?
}