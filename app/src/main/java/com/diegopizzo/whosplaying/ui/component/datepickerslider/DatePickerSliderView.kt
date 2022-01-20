package com.diegopizzo.whosplaying.ui.component.datepickerslider

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.blueDark
import com.diegopizzo.whosplaying.ui.component.attr.blueDark3
import com.diegopizzo.whosplaying.ui.component.attr.smallPadding
import com.diegopizzo.whosplaying.ui.component.attr.tinyPadding
import com.diegopizzo.whosplaying.ui.component.common.DefaultText
import com.diegopizzo.whosplaying.ui.component.common.MyCard
import com.diegopizzo.whosplaying.ui.component.common.MyDivider
import com.diegopizzo.whosplaying.ui.component.common.SmallText
import com.diegopizzo.whosplaying.ui.component.datepickerslider.DateSlider.NumberItemsVisible
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

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
                .background(if (isSelected) blueDark3 else blueDark)
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
    onDaySelected: ((date: LocalDate) -> Unit)? = null,
    numberItemsVisible: NumberItemsVisible = NumberItemsVisible.FIVE
) {
    val dateSliderData = createDatePickerSliderModel()
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
                val itemSelected = remember { mutableStateOf(dateSliderData.indexItemCurrentDay) }
                val year = remember { mutableStateOf(dateSliderData.currentYear) }
                val month = remember { mutableStateOf(dateSliderData.currentMonth) }

                fun onDayClicked(daySlider: DateSlider.DaySlider, index: Int) = run {
                    val offset = numberItemsVisible.offset

                    itemSelected.value = daySliderList.indexOf(daySlider)
                    year.value = daySlider.year
                    month.value = daySlider.month
                    onDaySelected?.invoke(daySlider.fullDate)
                    scope.launch {
                        listState.animateScrollToItem(index - offset)
                    }
                }

                BoxWithConstraints(Modifier.fillMaxWidth(), contentAlignment = Center) {
                    Row {
                        SmallText(
                            text = month.value,
                            Modifier.padding(vertical = tinyPadding, horizontal = tinyPadding)
                        )
                        SmallText(
                            text = year.value,
                            Modifier.padding(vertical = tinyPadding)
                        )
                    }
                    SmallText(text = stringResource(R.string.today), modifier = Modifier
                        .clickable {
                            val indexCurrentDay = dateSliderData.indexItemCurrentDay
                            onDayClicked(daySliderList[indexCurrentDay], indexCurrentDay)
                        }
                        .align(Alignment.CenterEnd)
                        .padding(vertical = tinyPadding, horizontal = smallPadding))
                }
                LazyRow(state = listState) {

                    scope.launch {
                        listState.scrollToItem(itemSelected.value - numberItemsVisible.offset)
                    }
                    onDaySelected?.invoke(daySliderList[itemSelected.value].fullDate)

                    items(
                        items = daySliderList,
                        itemContent = {
                            DatePickerDay(
                                dayName = it.dayName,
                                dayNumber = it.dayNumber,
                                isSelected = daySliderList.indexOf(it) == itemSelected.value,
                                onClick = {
                                    onDayClicked(it, daySliderList.indexOf(it))
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
    DatePickerSlider()
}