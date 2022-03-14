package com.diegopizzo.network

import com.diegopizzo.network.CommonConstant.DATE_PATTERN
import com.diegopizzo.network.CommonConstant.DATE_TIME_PATTERN
import com.diegopizzo.network.CommonConstant.TIME_PATTERN
import com.diegopizzo.network.Util.toEndZoneDateTime
import com.diegopizzo.network.Util.toStartZoneDateTime
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class UtilTest {

    @Test
    fun convertUtcDateTimeToLocal_TimeConverted_assertEqualsTrue() {
        val date = Util.convertUtcDateTimeToLocal(
            "2021-10-01T18:45:00+00:00",
            ZoneOffset.UTC,
            TIME_PATTERN
        )
        assertEquals("18:45", date)
    }

    @Test
    fun convertUtcDateTimeToLocal_dateConverted_assertEqualsTrue() {
        val date = Util.convertUtcDateTimeToLocal(
            "2021-10-01T18:45:00+00:00",
            ZoneOffset.UTC,
            DATE_PATTERN
        )
        assertEquals("Fri, 1 Oct 2021", date)
    }

    @Test
    fun convertUtcDateTimeToLocal_dateTimeConverted_assertEqualsTrue() {
        val date = Util.convertUtcDateTimeToLocal(
            "2021-10-01T18:45:00+00:00",
            ZoneOffset.UTC,
            DATE_TIME_PATTERN
        )
        assertEquals("01/10/2021 18:45", date)
    }

    @Test
    fun toStartZoneDateTime_dateConverted_assertEqualsTrue() {
        val localDate = LocalDate.of(2021, 11, 12)
        val actual = localDate.toStartZoneDateTime()
        val expected = ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneOffset.UTC)
        assertEquals(expected, actual)
    }

    @Test
    fun toEndZoneDateTime_dateConverted_assertEqualsTrue() {
        val localDate = LocalDate.of(2021, 11, 12)
        val actual = localDate.toEndZoneDateTime()
        val expected = ZonedDateTime.of(localDate, LocalTime.MAX, ZoneOffset.UTC)
        assertEquals(expected, actual)
    }
}