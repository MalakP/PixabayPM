package com.example.pixabaypm.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.pixabaypm.ui.navigation.PixaNavHost
import com.example.pixabaypm.ui.theme.PixabayPMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PixabayPMTheme {
                PixaNavHost(navController = navController)
            }
        }
    }
}


