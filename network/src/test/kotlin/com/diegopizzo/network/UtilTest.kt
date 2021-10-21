package com.diegopizzo.network

import com.diegopizzo.network.CommonConstant.DATE_PATTERN
import com.diegopizzo.network.CommonConstant.TIME_PATTERN
import org.junit.Assert
import org.junit.Test
import org.threeten.bp.ZoneId

class UtilTest {

    @Test
    fun convertUtcDateTimeToLocalTime_dateConverted_assertEqualsTrue() {
        val date = Util.convertUtcDateTimeToLocalTime(
            "2021-10-01T18:45:00+00:00",
            ZoneId.systemDefault(),
            TIME_PATTERN
        )
        Assert.assertEquals("19:45", date)
    }

    @Test
    fun convertUtcDateTimeToLocalDate_dateConverted_assertEqualsTrue() {
        val date = Util.convertUtcDateTimeToLocalDate(
            "2021-10-01T18:45:00+00:00",
            ZoneId.systemDefault(),
            DATE_PATTERN
        )
        Assert.assertEquals("Fri, 1 Oct 2021", date)
    }

    @Test
    fun areDatesWithoutTimesEquals_assertEqualsTrue() {
        val isEquals = Util.areDatesWithoutTimesEquals(
            "2021-10-01T18:45:00+00:00",
            "2021-10-01T17:45:00+00:00"
        )
        Assert.assertEquals(isEquals, true)
    }

    @Test
    fun areDatesWithoutTimesEquals_assertEqualsFalse() {
        val isEquals = Util.areDatesWithoutTimesEquals(
            "2021-10-01T18:45:00+00:00",
            "2021-10-02T18:45:00+00:00"
        )
        Assert.assertEquals(isEquals, false)
    }
}