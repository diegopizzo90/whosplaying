package com.diegopizzo.whosplaying.ui.component.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.whosplaying.ui.component.attr.blueDark
import com.diegopizzo.whosplaying.ui.component.attr.white

@Composable
fun MyAppTopBar(
    title: String,
    navigationOnClick: (() -> Unit)? = null,
    icon: ImageVector = Icons.Default.ArrowBack,
    actions: @Composable RowScope.() -> Unit = {},
) {
    Column {
        TopAppBar(
            title = { DefaultText(text = title) },
            backgroundColor = blueDark,
            contentColor = white,
            navigationIcon = {
                IconButton(onClick = { navigationOnClick?.invoke() }, content = {
                    Icon(imageVector = icon, contentDescription = "")
                })
            },
            actions = actions
        )
        MyDivider()
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    MyAppTopBar(title = "App Name")
}