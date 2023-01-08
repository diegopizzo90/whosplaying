package com.diegopizzo.network

import org.threeten.bp.LocalDate

object CommonConstant {
    const val DATE_PATTERN = "EEE, d MMM yyyy"
    const val TIME_PATTERN = "HH:mm"
    const val DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm"
    private const val END_OF_SEASON = 6
    val SEASON = getSeasonYear()

    private fun getSeasonYear(): String {
        val actualDate = LocalDate.now()
        val actualMonth = actualDate.monthValue
        return if (actualMonth == END_OF_SEASON) {
            actualDate.year
        } else {
            actualDate.year - 1
        }.toString()
    }
}