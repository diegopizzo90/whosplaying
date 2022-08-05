package com.diegopizzo.network

import org.threeten.bp.LocalDate

object CommonConstant {
    const val DATE_PATTERN = "EEE, d MMM yyyy"
    const val TIME_PATTERN = "HH:mm"
    val SEASON = LocalDate.now().year.toString()
    const val DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm"
}