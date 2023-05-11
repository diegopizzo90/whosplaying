package com.diegopizzo.whosplaying.ui.component.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.smallPadding
import com.diegopizzo.whosplaying.ui.component.attr.teal700

@Composable
fun ComposeImage(
    logoUrl: String,
    modifier: Modifier = Modifier,
    @DrawableRes placeholder: Int = R.drawable.ic_goal,
    contentScale: ContentScale = ContentScale.Fit
) {
    AsyncImage(
        model = logoUrl,
        contentDescription = null,
        placeholder = painterResource(placeholder),
        fallback = painterResource(placeholder),
        error = painterResource(placeholder),
        modifier = modifier,
        contentScale = contentScale,
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
    Box(modifier = Modifier.fillMaxSize()) {
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
    LoadingView()
}