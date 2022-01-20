package com.diegopizzo.whosplaying.ui.component.datepickerslider

import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.TextStyle
import java.util.*

data class DateSlider(
    val currentYear: String,
    val currentMonth: String,
    val daySlider: List<DaySlider>,
    val indexItemCurrentDay: Int
) {
    data class DaySlider(
        val dayName: String,
        val dayNumber: String,
        val month: String,
        val year: String,
        val fullDate: LocalDate
    )

    enum class NumberItemsVisible(val value: Int, val offset: Int) {
        ONE(1, 0), THREE(3, 1), FIVE(5, 2)
    }
}

fun itemPercentage(numberItemsVisible: Int): Float = 1.div(numberItemsVisible.toFloat())

fun createDatePickerSliderModel(): DateSlider {
    val now = LocalDate.now(ZoneId.systemDefault())
    val startDate = now.minusDays(5L)
    val endDate = now.plusDays(5L)

    val list = mutableListOf(startDate)
    var newDate = startDate.plusDays(1)
    while (newDate.isBefore(endDate)) {
        list.add(newDate)
        newDate = newDate.plusDays(1)
    }
    list.add(endDate)

    return DateSlider(
        now.year.toString(),
        now.month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
        list.map {
            DateSlider.DaySlider(
                it.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                it.dayOfMonth.toString(),
                it.month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                it.year.toString(),
                it
            )
        }, 5
    )
}