package com.diegopizzo.whosplaying.ui.component.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.teal700

@Composable
fun ComposeImage(
    logoUrl: String,
    modifier: Modifier = Modifier,
    @DrawableRes placeholder: Int = R.drawable.ic_goal
) {
    Image(
        painter = rememberImagePainter(
            data = logoUrl,
            builder = {
                placeholder(placeholder)
            }
        ),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun MyDivider(modifier: Modifier = Modifier, color: Color = teal700, thickness: Dp = 1.dp) {
    Divider(color = color, modifier = modifier, thickness = thickness)
}