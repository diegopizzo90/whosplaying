package com.diegopizzo.whosplaying.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diegopizzo.whosplaying.database.entity.FixtureEntity

@Dao
interface FixtureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFixture(vararg fixture: FixtureEntity)

    @Query("SELECT * FROM fixture")
    fun getAll(): List<FixtureEntity>
}