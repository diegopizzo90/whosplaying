package com.diegopizzo.whosplaying.ui.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.whosplaying.ui.component.attr.*

@Composable
fun MyCard(
    content: @Composable () -> Unit,
    onClick: (() -> Unit)? = null
) {
    Card(
        backgroundColor = MaterialTheme.colors.card,
        content = content,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(smallPadding)
            .clickable { onClick?.invoke() }
    )
}

@Composable
fun MyRow(
    content: @Composable RowScope.() -> Unit,
    horizontalArrangement: Arrangement.Horizontal,
    onClick: (() -> Unit)? = null,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            content = content,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = MaterialTheme.colors.row)
                .clickable { onClick?.invoke() },
            horizontalArrangement = horizontalArrangement
        )
        Divider(
            color = teal700,
        )
    }
}

@Composable
fun MySurface(content: @Composable () -> Unit) {
    Surface(color = blueDark2) {
        content.invoke()
    }
}

@Composable
fun MyScaffold(
    content: @Composable () -> Unit, modifier: Modifier = Modifier,
    navigationOnClick: (() -> Unit)? = null,
    icon: ImageVector = Icons.Default.ArrowBack, title: String,
) {
    Scaffold(
        modifier = modifier,
        backgroundColor = blueDark2,
        topBar = { MyAppTopBar(title = title, navigationOnClick, icon) }) {
        content.invoke()
    }
}

@Preview
@Composable
fun Preview() {
    MyScaffold(content = {}, title = "App Name")
}