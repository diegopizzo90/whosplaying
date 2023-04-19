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
import com.diegopizzo.whosplaying.ui.component.datepickerslider.DatePickerSliderModel.NumberItemsVisible
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
    dateSliderData: List<DatePickerSliderModel>,
    indexItemSelected: Int,
    onDaySelected: ((dateSelected: DatePickerSliderModel) -> Unit),
    numberItemsVisible: NumberItemsVisible = NumberItemsVisible.FIVE
) {
    val currentDate = dateSliderData.currentDateItem()
    MyCard(
        elevation = 0.dp,
        shape = RoundedCornerShape(0.dp),
        padding = 0.dp,
        backgroundColor = blueDark,
        content = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                val scope = rememberCoroutineScope()
                val itemSelected = remember { mutableStateOf(indexItemSelected) }
                val year = remember { mutableStateOf(dateSliderData[indexItemSelected].year) }
                val month = remember { mutableStateOf(dateSliderData[indexItemSelected].month) }
                val listState = rememberLazyListState(indexItemSelected - numberItemsVisible.offset)

                fun onDayClicked(daySelectedModel: DatePickerSliderModel) = run {
                    val offset = numberItemsVisible.offset

                    itemSelected.value = daySelectedModel.index
                    year.value = daySelectedModel.year
                    month.value = daySelectedModel.month
                    scope.launch {
                        val scrollIndex = daySelectedModel.index - offset
                        listState.animateScrollToItem(if (scrollIndex > 0) scrollIndex else 0)
                    }
                    onDaySelected(daySelectedModel)
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
                            onDayClicked(currentDate)
                        }
                        .align(Alignment.CenterEnd)
                        .padding(vertical = tinyPadding, horizontal = smallPadding))
                }
                LazyRow(state = listState) {
                    //Notify view for the default day selected
                    onDaySelected(dateSliderData[indexItemSelected])
                    items(
                        items = dateSliderData,
                        itemContent = {
                            DatePickerDay(
                                dayName = it.dayName,
                                dayNumber = it.dayNumber,
                                isSelected = dateSliderData.indexOf(it) == itemSelected.value,
                                onClick = { onDayClicked(it) },
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
private fun DatePickerDayPreview() {
    DatePickerDay("WED", "23")
}

@Preview
@Composable
private fun DatePickerDayPreview2() {
    DatePickerDay("WED", "23", isSelected = true)
}

@Preview
@Composable
private fun DatePickerSliderPreview() {
    DatePickerSlider(createDatePickerSliderModel(), 5, {})
}