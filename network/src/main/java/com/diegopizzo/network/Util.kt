package com.diegopizzo.network

import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

object Util {
    fun convertUtcDateTimeToLocalDate(
        utcDate: String,
        timeZone: ZoneId,
        datePattern: String
    ): String {
        return ZonedDateTime.parse(utcDate, DateTimeFormatter.ISO_DATE_TIME)
            .withZoneSameInstant(timeZone)
            .format(
                DateTimeFormatter.ofPattern(datePattern)
            )
    }

    fun convertUtcDateTimeToLocalTime(
        utcDate: String,
        timeZone: ZoneId,
        timePattern: String
    ): String {
        return ZonedDateTime.parse(utcDate, DateTimeFormatter.ISO_DATE_TIME)
            .withZoneSameInstant(timeZone)
            .format(
                DateTimeFormatter.ofPattern(timePattern)
            )
    }

    fun areDatesWithoutTimesEquals(firstDateTime: String, secondDateTime: String): Boolean {
        val firstUtcDate = ZonedDateTime.parse(firstDateTime, DateTimeFormatter.ISO_DATE_TIME)
        val secondUtcDate = ZonedDateTime.parse(secondDateTime, DateTimeFormatter.ISO_DATE_TIME)
        return firstUtcDate.toLocalDate().isEqual(secondUtcDate.toLocalDate())
    }
}