package com.diegopizzo.whosplaying.ui.component.datepickerslider

import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.TextStyle
import java.util.*

data class DatePickerSliderModel(
    val index: Int,
    val dayName: String,
    val dayNumber: String,
    val month: String,
    val year: String,
    val fullDate: LocalDate,
    val isCurrentDate: Boolean = false
) {

    enum class NumberItemsVisible(val value: Int, val offset: Int) {
        ONE(1, 0), THREE(3, 1), FIVE(5, 2)
    }
}

fun createDatePickerSliderModel(zoneId: ZoneId = ZoneId.systemDefault()): List<DatePickerSliderModel> {
    val now = LocalDate.now(zoneId)
    val startDate = now.minusDays(7L)
    val endDate = now.plusDays(7L)

    val list = mutableListOf(startDate)
    var newDate = startDate.plusDays(1)
    while (newDate.isBefore(endDate)) {
        list.add(newDate)
        newDate = newDate.plusDays(1)
    }
    list.add(endDate)

    return list.map {
        DatePickerSliderModel(
            list.indexOf(it),
            it.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            it.dayOfMonth.toString(),
            it.month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
            it.year.toString(),
            it,
            it.isEqual(now)
        )
    }
}

fun List<DatePickerSliderModel>.indexCurrentDate() = indexOfFirst { it.isCurrentDate }
fun List<DatePickerSliderModel>.currentDateItem() = first { it.isCurrentDate }

fun itemPercentage(numberItemsVisible: Int): Float = 1.div(numberItemsVisible.toFloat())