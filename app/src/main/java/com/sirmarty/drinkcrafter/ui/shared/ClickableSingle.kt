package com.sirmarty.drinkcrafter.ui.shared

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Modifier.clickableSingle(
    onClick: () -> Unit
): Modifier {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    return this.clickable(
        onClick = { multipleEventsCutter.processEvent { onClick() } },
        indication = LocalIndication.current,
        interactionSource = remember { MutableInteractionSource() }
    )
}