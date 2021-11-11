package com.diegopizzo.whosplaying.ui.component.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.diegopizzo.whosplaying.ui.component.attr.textColor

@Composable
fun SuperTinyText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, fontSize = 11.sp, color = MaterialTheme.colors.textColor, modifier = modifier)
}

@Composable
fun TinyText(text: String) {
    Text(text = text, fontSize = 12.sp, color = MaterialTheme.colors.textColor)
}

@Composable
fun SmallText(text: String, modifier: Modifier = Modifier, fontWeight: FontWeight? = null) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = MaterialTheme.colors.textColor,
        fontWeight = fontWeight,
        modifier = modifier
    )
}

@Composable
fun MediumText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, fontSize = 16.sp, color = MaterialTheme.colors.textColor, modifier = modifier)
}

@Composable
fun DefaultText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, fontSize = 22.sp, color = MaterialTheme.colors.textColor, modifier = modifier)
}

@Composable
fun LargeText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, fontSize = 30.sp, color = MaterialTheme.colors.textColor, modifier = modifier)
}