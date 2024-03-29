package com.diegopizzo.whosplaying.ui.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.diegopizzo.whosplaying.R
import com.diegopizzo.whosplaying.ui.component.attr.blueDark2
import com.diegopizzo.whosplaying.ui.component.attr.smallPadding

@Composable
fun MyCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    elevation: Dp = 1.dp,
    shape: Shape = MaterialTheme.shapes.medium,
    padding: Dp = smallPadding,
    backgroundColor: Color = MaterialTheme.colors.surface,
    content: @Composable () -> Unit,
) {
    Card(
        backgroundColor = backgroundColor,
        content = content,
        elevation = elevation,
        shape = shape,
        modifier = modifier
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
                .background(color = MaterialTheme.colors.surface)
                .clickable { onClick?.invoke() },
            horizontalArrangement = horizontalArrangement,
        )
        MyDivider()
    }
}

@Composable
fun MyScaffold(
    modifier: Modifier = Modifier,
    navigationOnClick: (() -> Unit)? = null,
    icon: ImageVector = Icons.Default.ArrowBack,
    title: String = stringResource(R.string.app_name),
    content: @Composable (paddingValues: PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        backgroundColor = blueDark2,
        topBar = {
            MyAppTopBar(
                title = title,
                icon = icon,
                navigationOnClick = navigationOnClick,
            )
        }) {
        content.invoke(it)
    }
}

@Preview
@Composable
fun Preview() {
    MyScaffold(content = {}, title = "App Name")
}