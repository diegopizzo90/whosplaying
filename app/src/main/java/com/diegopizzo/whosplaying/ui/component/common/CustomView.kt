package com.diegopizzo.whosplaying.ui.component.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter
import com.diegopizzo.whosplaying.R

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