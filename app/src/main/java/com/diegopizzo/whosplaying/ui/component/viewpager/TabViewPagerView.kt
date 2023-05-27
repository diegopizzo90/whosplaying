package com.diegopizzo.whosplaying.ui.component.viewpager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.whosplaying.ui.component.attr.blueDark2
import com.diegopizzo.whosplaying.ui.component.common.MediumText
import com.diegopizzo.whosplaying.ui.component.common.SmallText
import com.diegopizzo.whosplaying.ui.detailsscreen.TabPager
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun TabViewPager(
    tabList: List<TabPager>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = blueDark2,
    content: @Composable (tabViewSelectedIndex: Int) -> Unit
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Column(modifier = modifier) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = backgroundColor,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            tabList.forEachIndexed { index, tab ->
                Tab(selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                    text = {
                        SmallText(stringResource(id = tab.tabNameRes))
                    }
                )
            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
        ) { index ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                content.invoke(index)
            }
        }
    }

/*    LaunchedEffect(pagerState.currentPage) {
        tabSelected.value = tabList[pagerState.currentPage]
    }*/
}

@ExperimentalPagerApi
@Preview
@Composable
fun TabViewPagerPreview() {
    TabViewPager(
        tabList = listOf(
            TabPager.MatchEvents,
            TabPager.Statistics,
        )
    ) { tabViewSelectedIndex ->
        MediumText(
            text = "tabViewSelectedIndex: $tabViewSelectedIndex"
        )
    }
}