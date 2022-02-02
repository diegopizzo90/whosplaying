package com.diegopizzo.database.converter

import androidx.room.TypeConverter
import com.diegopizzo.network.Util.dateTimeFormatter
import org.threeten.bp.ZonedDateTime

class ZonedDateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: String): ZonedDateTime {
        return ZonedDateTime.parse(value, dateTimeFormatter)
    }

    @TypeConverter
    fun fromLocalDate(date: ZonedDateTime): String {
        return date.format(dateTimeFormatter)
    }
}