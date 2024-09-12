package com.sirmarty.drinkcrafter.ui.components.customtopappbar

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import com.sirmarty.drinkcrafter.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    modifier: Modifier,
    context: Context,
    customTopAppBarState: CustomTopAppBarState,
    title: String,
    onBackClick: () -> Unit
) {
    val view = LocalView.current
    val window = (context as? Activity)?.window
    val darkTheme = isSystemInDarkTheme()
    var isAppearanceLightStatusBars by remember { mutableStateOf(false) }

    val containerColor: Color
    val contentColor: Color
    val showTitle: Boolean

    when (customTopAppBarState) {
        CustomTopAppBarState.TRANSPARENT -> {
            containerColor = Color.Transparent
            contentColor = Color.White
            showTitle = false
            isAppearanceLightStatusBars = false
        }
        CustomTopAppBarState.SOLID -> {
            containerColor = Color.White
            contentColor = Color.Black
            showTitle = false
            isAppearanceLightStatusBars = !darkTheme
        }
        CustomTopAppBarState.SOLID_WITH_TITLE -> {
            containerColor = Color.White
            contentColor = Color.Black
            showTitle = true
            isAppearanceLightStatusBars = !darkTheme
        }
    }

    // Change status bar content color to match top bar content color
    val windowInsetsController = window?.let { WindowInsetsControllerCompat(it, view) }
    windowInsetsController?.isAppearanceLightStatusBars = isAppearanceLightStatusBars


    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            if (showTitle) {
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = contentColor
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.clip(CircleShape),
                colors = IconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = contentColor,
                    // It should never be disabled
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = context.getString(R.string.drink_detail_back_arrow)
                )
            }
        }
    )
}