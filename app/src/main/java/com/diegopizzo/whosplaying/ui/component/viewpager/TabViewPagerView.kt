package com.diegopizzo.whosplaying.ui.component.viewpager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.whosplaying.ui.component.attr.blueDark2
import com.diegopizzo.whosplaying.ui.component.common.MediumText
import com.diegopizzo.whosplaying.ui.component.common.MyCard
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun TabViewPager(
    tabList: List<String>,
    backgroundColor: Color = blueDark2,
    content: @Composable (tabNameSelected: String) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = tabList.size)
    val scope = rememberCoroutineScope()
    val tabIndex = pagerState.currentPage
    val tabSelected = remember { mutableStateOf(tabList.first()) }
    Column {
        TabRow(
            selectedTabIndex = tabIndex,
            backgroundColor = backgroundColor,
            contentColor = Color.White
        ) {
            tabList.forEachIndexed { index, tab ->
                Tab(selected = tabIndex == index, onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                        tabSelected.value = tabList[index]
                    }
                }, text = { MediumText(tab) })
            }
        }

        HorizontalPager(state = pagerState) { index ->
            MyCard(content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    tabSelected.value = tabList[index]
                    content.invoke(tabSelected.value)
                }
            })
        }
    }
}

@ExperimentalPagerApi
@Preview
@Composable
fun TabViewPagerPreview() {
    TabViewPager(listOf("Match Events", "Statistics"), content = {
        MediumText(text = it)
    })
}