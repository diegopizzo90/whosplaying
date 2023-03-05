package com.diegopizzo.database.converter

import androidx.room.TypeConverter
import com.diegopizzo.network.interactor.league.LeagueType

class LeagueTypeConverter {
    @TypeConverter
    fun toLeagueType(value: String) = enumValueOf<LeagueType>(value)

    @TypeConverter
    fun fromLeagueType(value: LeagueType) = value.name
}