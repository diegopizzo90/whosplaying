package com.diegopizzo.whosplaying.ui.blinkingcircle

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.diegopizzo.whosplaying.ui.component.attr.blueDark3
import com.diegopizzo.whosplaying.ui.component.attr.aqua

@Composable
fun BlinkingCircleView(size: Dp = 10.dp) {
    val infiniteTransition = rememberInfiniteTransition()
    val color = infiniteTransition.animateColor(
        blueDark3, aqua, animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        )
    )

    Surface(
        color = color.value,
        shape = CircleShape,
        modifier = Modifier.size(size),
        content = {}
    )
}


@Preview
@Composable
private fun BlinkingCircleViewPreview() {
    BlinkingCircleView()
}