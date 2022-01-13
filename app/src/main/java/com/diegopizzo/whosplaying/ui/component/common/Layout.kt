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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.diegopizzo.whosplaying.ui.component.attr.*

@Composable
fun MyCard(
    content: @Composable () -> Unit,
    onClick: (() -> Unit)? = null,
    elevation: Dp = 1.dp,
    shape: Shape = MaterialTheme.shapes.medium,
    padding: Dp = smallPadding
) {
    Card(
        backgroundColor = MaterialTheme.colors.card,
        content = content,
        elevation = elevation,
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(padding)
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
            content = content,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = MaterialTheme.colors.row)
                .clickable { onClick?.invoke() },
            horizontalArrangement = horizontalArrangement
        )
        MyDivider()
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