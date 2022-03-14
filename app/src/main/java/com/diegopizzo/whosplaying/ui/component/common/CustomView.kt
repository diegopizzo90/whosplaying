package com.diegopizzo.whosplaying.ui.component.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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