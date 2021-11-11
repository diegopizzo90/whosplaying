package com.diegopizzo.whosplaying.ui.component.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.whosplaying.ui.component.attr.blueDark2
import com.diegopizzo.whosplaying.ui.component.attr.white

@Composable
fun MyAppTopBar(
    title: String,
    navigationOnClick: (() -> Unit)? = null,
    icon: ImageVector = Icons.Default.ArrowBack,
) {
    TopAppBar(
        title = { MediumText(text = title) },
        backgroundColor = blueDark2,
        contentColor = white,
        navigationIcon = {
            IconButton(onClick = { navigationOnClick?.invoke() }, content = {
                Icon(imageVector = icon, contentDescription = "")
            })
        }
    )
}

@Preview
@Composable
private fun TopBarPreview() {
    MyAppTopBar(title = "App Name")
}