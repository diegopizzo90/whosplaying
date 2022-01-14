package com.diegopizzo.whosplaying.ui.component.datepickerslider

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diegopizzo.whosplaying.ui.component.attr.*
import com.diegopizzo.whosplaying.ui.component.common.DefaultText
import com.diegopizzo.whosplaying.ui.component.common.MyCard
import com.diegopizzo.whosplaying.ui.component.common.MyDivider
import com.diegopizzo.whosplaying.ui.component.common.SmallText
import com.diegopizzo.whosplaying.ui.component.datepickerslider.DateSlider.DaySlider
import com.diegopizzo.whosplaying.ui.component.datepickerslider.DateSlider.NumberItemsVisible
import kotlinx.coroutines.launch

@Composable
private fun DatePickerDay(
    dayName: String,
    dayNumber: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    isSelected: Boolean = false
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.then(
            Modifier
                .background(if (isSelected) blueDark2 else blueDark)
                .clickable { onClick?.invoke() }
        )
    ) {
        SmallText(text = dayName.uppercase(), Modifier.padding(top = tinyPadding))
        DefaultText(
            text = dayNumber,
            modifier = Modifier
                .padding(horizontal = smallPadding)
                .padding(bottom = tinyPadding)
        )
    }
}

@Composable
fun DatePickerSlider(
    dateSliderData: DateSlider,
    optionText: String = "",
    onOptionClick: (() -> Unit)? = null,
    onDayClick: ((index: Int) -> Unit)? = null,
    numberItemsVisible: NumberItemsVisible = NumberItemsVisible.FIVE
) {
    MyCard(
        elevation = 0.dp,
        shape = RoundedCornerShape(0.dp),
        padding = 0.dp,
        backgroundColor = blueDark,
        content = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                val daySliderList = dateSliderData.daySlider
                val listState = rememberLazyListState()
                val scope = rememberCoroutineScope()
                val itemSelected = remember { mutableStateOf(dateSliderData.indexItemSelected) }

                BoxWithConstraints(Modifier.fillMaxWidth(), contentAlignment = Center) {
                    Row {
                        SmallText(
                            text = dateSliderData.year,
                            Modifier.padding(vertical = tinyPadding, horizontal = tinyPadding)
                        )
                        SmallText(
                            text = dateSliderData.month,
                            Modifier.padding(vertical = tinyPadding)
                        )
                    }
                    SmallText(
                        text = optionText,
                        modifier = Modifier
                            .clickable { onOptionClick?.invoke() }
                            .align(Alignment.CenterEnd)
                            .padding(vertical = tinyPadding, horizontal = smallPadding)
                            .clickable { onOptionClick?.invoke() })
                }
                LazyRow(state = listState) {

                    scope.launch {
                        listState.animateScrollToItem(itemSelected.value - numberItemsVisible.offset)
                    }

                    items(
                        items = daySliderList,
                        itemContent = {
                            DatePickerDay(
                                dayName = it.dayName,
                                dayNumber = it.dayNumber,
                                isSelected = daySliderList.indexOf(it) == itemSelected.value,
                                onClick = {
                                    itemSelected.value = daySliderList.indexOf(it)
                                    onDayClick?.invoke(daySliderList.indexOf(it))
                                    scope.launch {
                                        listState.animateScrollToItem(itemSelected.value - numberItemsVisible.offset)
                                    }
                                },
                                modifier = Modifier.fillParentMaxWidth(
                                    itemPercentage(numberItemsVisible.value)
                                )
                            )
                        }
                    )
                }
                MyDivider()
            }
        })
}

data class DateSlider(
    val year: String,
    val month: String,
    val daySlider: List<DaySlider>,
    val indexItemSelected: Int
) {
    data class DaySlider(
        val dayName: String,
        val dayNumber: String
    )

    enum class NumberItemsVisible(val value: Int, val offset: Int) {
        ONE(1, 0), THREE(3, 1), FIVE(5, 2)
    }
}

private fun itemPercentage(numberItemsVisible: Int): Float = 1.div(numberItemsVisible.toFloat())

@Preview
@Composable
fun DatePickerDayPreview() {
    DatePickerDay("WED", "23")
}

@Preview
@Composable
fun DatePickerDayPreview2() {
    DatePickerDay("WED", "23", isSelected = true)
}

@Preview
@Composable
fun DatePickerSliderPreview() {
    DatePickerSlider(dateSliderData, "Today")
}

val dateSliderData = DateSlider(
    "January", "2022", listOf(
        DaySlider("Sun", "17"),
        DaySlider("Mon", "18"),
        DaySlider("Tue", "19"),
        DaySlider("Wed", "20"),
        DaySlider("Thu", "21"),
        DaySlider("Fri", "22"),
        DaySlider("Sat", "23"),
        DaySlider("Sun", "24"),
        DaySlider("Mon", "25"),
        DaySlider("Tue", "26"),
        DaySlider("Wed", "27")
    ), 5
)