package com.sirmarty.drinkcrafter.ui.components.savebutton

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirmarty.drinkcrafter.R


/**
 *  When previewMode = true --> draw a fake button than can be rendered in previews
 *  When previewMode = false --> draw the real button with its viewModel injected
 */
@Composable
fun SaveButton(
    modifier: Modifier,
    drinkId: Int,
    previewMode: Boolean
) {
    if(previewMode) {
        MockSaveButton(modifier)
    } else {
        RealSaveButton(modifier, drinkId)
    }
}

@Composable
private fun RealSaveButton(
    modifier: Modifier,
    drinkId: Int,
    viewModel: SaveButtonViewModel = hiltViewModel()
) {
    viewModel.setId(drinkId)

    val isSaved = viewModel.isSaved.collectAsState()
    val context = LocalContext.current

    IconButton(
        modifier = modifier,
        onClick = { viewModel.onButtonClick() },
        colors = IconButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            // It should never be disabled
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified
        )
    ) {
        val iconResource = if (isSaved.value) R.drawable.ic_saved_filled else R.drawable.ic_saved
        Icon(
            painterResource(iconResource),
            contentDescription = context.getString(R.string.save_button_icon)
        )
    }
}

@Composable
private fun MockSaveButton(
    modifier: Modifier,
) {
    IconButton(
        modifier = modifier,
        onClick = { },
        colors = IconButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            // It should never be disabled
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified
        )
    ) {
        Icon(
            painterResource(R.drawable.ic_saved_filled),
            contentDescription = "Mock"
        )
    }
}