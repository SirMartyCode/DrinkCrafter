package com.sirmarty.drinkcrafter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.sirmarty.drinkcrafter.ui.navigation.MainNavHost
import com.sirmarty.drinkcrafter.ui.theme.DrinkCrafterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrinkCrafterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        // Allowing edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DrinkCrafterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MainNavHost()
                }
            }
        }
    }
}