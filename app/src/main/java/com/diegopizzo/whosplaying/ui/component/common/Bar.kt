package com.diegopizzo.whosplaying.ui.component.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.whosplaying.ui.component.attr.blueDark
import com.diegopizzo.whosplaying.ui.component.attr.white

@Composable
fun MyAppTopBar(
    title: String,
    icon: ImageVector? = null,
    navigationOnClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    Column {
        if (icon != null) {
            AppTopBar(
                title = title,
                icon = icon,
                navigationOnClick = navigationOnClick,
                actions = actions
            )
        } else {
            AppTopBar(
                title = title,
                actions = actions,
            )
        }
        MyDivider()
    }
}

@Composable
private fun AppTopBar(
    title: String,
    icon: ImageVector? = null,
    navigationOnClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { DefaultText(text = title) },
        backgroundColor = blueDark,
        contentColor = white,
        navigationIcon = {
            icon?.let { iconImageVector ->
                IconButton(
                    onClick = { navigationOnClick?.invoke() }) {
                    Icon(imageVector = iconImageVector, contentDescription = "")
                }
            }
        },
        actions = actions
    )
}

@Composable
private fun AppTopBar(
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { DefaultText(text = title) },
        backgroundColor = blueDark,
        contentColor = white,
        actions = actions
    )
}

@Preview
@Composable
private fun TopBarPreview() {
    MyAppTopBar(title = "App Name")
}