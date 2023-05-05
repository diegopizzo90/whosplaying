package com.diegopizzo.whosplaying.ui.component.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.diegopizzo.whosplaying.ui.component.attr.textColor

@Composable
fun SuperTinyText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, fontSize = 11.sp, color = MaterialTheme.colors.textColor, modifier = modifier)
}

@Composable
fun TinyText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = 12.sp,
        color = MaterialTheme.colors.textColor,
        maxLines = maxLines,
        minLines = minLines,
    )
}

@Composable
fun SmallText(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = MaterialTheme.colors.textColor,
        fontWeight = fontWeight,
        modifier = modifier,
        textAlign = textAlign,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun MediumText(text: String, modifier: Modifier = Modifier, fontWeight: FontWeight? = null) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = MaterialTheme.colors.textColor,
        modifier = modifier,
        fontWeight = fontWeight
    )
}

@Composable
fun DefaultText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    fontWeight: FontWeight? = null
) {
    Text(
        text = text,
        fontSize = 22.sp,
        color = MaterialTheme.colors.textColor,
        modifier = modifier,
        textAlign = textAlign,
        fontWeight = fontWeight
    )
}

@Composable
fun LargeText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, fontSize = 30.sp, color = MaterialTheme.colors.textColor, modifier = modifier)
}