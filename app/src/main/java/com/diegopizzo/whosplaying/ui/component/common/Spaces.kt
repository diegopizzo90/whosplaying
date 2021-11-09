package com.diegopizzo.whosplaying.ui.component.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.diegopizzo.whosplaying.ui.component.attr.defaultPadding
import com.diegopizzo.whosplaying.ui.component.attr.largePadding
import com.diegopizzo.whosplaying.ui.component.attr.smallPadding
import com.diegopizzo.whosplaying.ui.component.attr.tinyPadding

@Composable
fun LargeSpacer() {
    Spacer(modifier = Modifier.size(largePadding))
}

@Composable
fun NormalSpacer() {
    Spacer(modifier = Modifier.size(defaultPadding))
}

@Composable
fun SmallSpacer() {
    Spacer(modifier = Modifier.size(smallPadding))
}

@Composable
fun TinySpacer() {
    Spacer(modifier = Modifier.size(tinyPadding))
}