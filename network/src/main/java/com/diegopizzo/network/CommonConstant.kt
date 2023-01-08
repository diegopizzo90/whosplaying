package com.diegopizzo.network

import org.threeten.bp.LocalDate

object CommonConstant {
    const val DATE_PATTERN = "EEE, d MMM yyyy"
    const val TIME_PATTERN = "HH:mm"
    val SEASON = getSeasonYear()
    const val DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm"


    private fun getSeasonYear(): String {
        val actualDate = LocalDate.now()
        val actualMonth = actualDate.monthValue
        return if (actualMonth == 6) {
            actualDate.year
        } else {
            actualDate.year - 1
        }.toString()
    }
}