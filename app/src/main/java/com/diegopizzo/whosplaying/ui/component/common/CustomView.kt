package com.diegopizzo.whosplaying.ui.component.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.teal700

@Composable
fun ComposeImage(
    logoUrl: String,
    modifier: Modifier = Modifier,
    @DrawableRes placeholder: Int = R.drawable.ic_goal
) {
    AsyncImage(
        model = logoUrl,
        contentDescription = null,
        placeholder = painterResource(placeholder),
        fallback = painterResource(placeholder),
        error = painterResource(placeholder),
        modifier = modifier
    )
}

@Composable
fun MyDivider(modifier: Modifier = Modifier, color: Color = teal700, thickness: Dp = 1.dp) {
    Divider(color = color, modifier = modifier, thickness = thickness)
}

@Composable
fun VerticalDivider(modifier: Modifier) {
    MyDivider(modifier = modifier.then(Modifier.width(1.dp)))
}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MediumText(stringResource(R.string.loading))
            CircularProgressIndicator(color = Color.White)
        }
    }
}

@Preview
@Composable
fun LoadingPreview() {
    LoadingView()
}