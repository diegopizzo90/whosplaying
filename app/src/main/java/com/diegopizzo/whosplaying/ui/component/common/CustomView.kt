package com.diegopizzo.whosplaying.ui.component.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.WhosPlayingTheme
import com.diegopizzo.whosplaying.ui.component.attr.smallPadding
import com.diegopizzo.whosplaying.ui.component.attr.teal700

@Composable
fun AppImage(
    painter: PainterViewData,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    Image(
        painter = painter(),
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
    )
}

@Composable
fun AppIcon(
    painter: PainterViewData,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Color.Unspecified,
) {
    Icon(
        painter = painter(),
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
    )
}

@Composable
fun MyDivider(modifier: Modifier = Modifier, color: Color = teal700, thickness: Dp = 1.dp) {
    Divider(color = color, modifier = modifier, thickness = thickness)
}

@Composable
fun VerticalDivider(modifier: Modifier = Modifier, color: Color = teal700, thickness: Dp = 1.dp) {
    MyDivider(
        modifier = modifier.width(1.dp),
        color = color,
        thickness = thickness,
    )
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MediumText(
                stringResource(R.string.loading),
                modifier = Modifier.padding(bottom = smallPadding)
            )
            LinearProgressIndicator(color = Color.White)
        }
    }
}

@Preview
@Composable
fun LoadingPreview() {
    WhosPlayingTheme {
        LoadingView()
    }
}