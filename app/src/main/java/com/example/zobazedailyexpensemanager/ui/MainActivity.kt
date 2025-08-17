package com.example.zobazedailyexpensemanager.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowInsetsControllerCompat
import com.example.zobazedailyexpensemanager.core.MyAppTheme
import com.example.zobazedailyexpensemanager.ui.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.toArgb
import com.example.zobazedailyexpensemanager.core.DarkColors
import com.example.zobazedailyexpensemanager.core.LightColors

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SetStatusBarColor()
                    AppNavigation()
                }
            }
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun SetStatusBarColor() {
    val activity = LocalContext.current as Activity
    val isDarkTheme = isSystemInDarkTheme()

    SideEffect {
        activity.window.statusBarColor = if (isDarkTheme) {
            DarkColors.primary.toArgb()
        } else {
            LightColors.primary.toArgb()
        }

        WindowInsetsControllerCompat(
            activity.window,
            activity.window.decorView
        ).isAppearanceLightStatusBars = isDarkTheme
    }
}