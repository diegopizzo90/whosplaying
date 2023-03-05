package com.diegopizzo.network

import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter

object Util {

    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    fun convertUtcDateTimeToLocal(
        utcDate: String,
        timeZone: ZoneId,
        pattern: String
    ): String {
        return ZonedDateTime.parse(utcDate, dateTimeFormatter)
            .withZoneSameInstant(timeZone)
            .format(DateTimeFormatter.ofPattern(pattern))
    }

    fun LocalDate.toStartZoneDateTime(): ZonedDateTime {
        return this.atStartOfDay(ZoneOffset.UTC)
    }

    fun LocalDate.toEndZoneDateTime(): ZonedDateTime {
        return this.atStartOfDay(ZoneOffset.UTC).with(LocalTime.MAX)
    }
}