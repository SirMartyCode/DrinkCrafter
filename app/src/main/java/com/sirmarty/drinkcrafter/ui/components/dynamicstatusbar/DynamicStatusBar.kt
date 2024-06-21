package com.sirmarty.drinkcrafter.ui.components.dynamicstatusbar

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

@Composable
fun DynamicStatusBarWithImage(imageUrl: String) {
    val context = LocalContext.current
    val view = LocalView.current
    val window = (context as? Activity)?.window
    val darkTheme = isSystemInDarkTheme()

    DisposableEffect(imageUrl) {
        val target = object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                // Since we want to know what color is the part of the image that overlaps
                // with the status bar, we will only take the upper left part of its bitmap
                val upperLeftBitmap = getUpperLeftBitmap(resource)

                val palette = Palette.from(upperLeftBitmap).generate()
                val dominantColor = palette.dominantSwatch?.rgb ?: Color.White.toArgb()

                val isDark = isColorDark(dominantColor)
                val windowInsetsController = window?.let { WindowInsetsControllerCompat(it, view) }
                windowInsetsController?.isAppearanceLightStatusBars = !isDark
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                // Nothing to do
            }
        }

        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .into(target)

        onDispose {
            Glide.with(context).clear(target)

            // Set the default color when screen closed
            val windowInsetsController = window?.let { WindowInsetsControllerCompat(it, view) }
            windowInsetsController?.isAppearanceLightStatusBars = !darkTheme
        }
    }
}

private fun getUpperLeftBitmap(bitmap: Bitmap): Bitmap {
    val width = bitmap.width / 3
    val height = bitmap.height / 5
    return Bitmap.createBitmap(bitmap, 0, 0, width, height)
}

/**
 * 1. Getting the primary colors
 * 2. Calculate brightness (0.299 * R + 0.587 * G + 0.114 * B)
 * 3. Normalize brightness by dividing by 255
 * 4. Subtract it from 1 to obtain a measurement of "darkness" fom 1 to 0
 * where 1 = complete dark, 0 = complete light
 */
private fun isColorDark(color: Int): Boolean {
    val red = ((color shr 16) and 0xFF)
    val green = ((color shr 8) and 0xFF)
    val blue = (color and 0xFF)
    val darkness = 1 - (0.299 * red + 0.587 * green + 0.114 * blue) / 255
    return darkness >= 0.5
}