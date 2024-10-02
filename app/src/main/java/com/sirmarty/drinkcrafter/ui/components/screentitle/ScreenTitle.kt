package com.sirmarty.drinkcrafter.ui.components.screentitle

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@Composable
fun ScreenTitle(@StringRes stringRes: Int, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Text(
        text = context.getString(stringRes),
        modifier = modifier,
        fontSize = 22.sp,
        textDecoration = TextDecoration.Underline,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun ScreenTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = 22.sp,
        textDecoration = TextDecoration.Underline,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onPrimary
    )
}