package com.scoqu.lasw.egasy.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.scoqu.lasw.egasy.presentation.location.LocationScreen
import com.scoqu.lasw.egasy.presentation.util.Screen
import com.scoqu.lasw.egasy.presentation.weather.WeatherScreen
import com.scoqu.lasw.egasy.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(Color(0xFF03161B), Color(0xFF025E73)),
                                    center = Offset(
                                        (resources.displayMetrics.widthPixels / 2f),
                                        resources.displayMetrics.heightPixels.toFloat()
                                    ),
                                    radius = resources.displayMetrics.heightPixels.toFloat()
                                ),
                            )
                    ) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Screen.CityScreen.route
                        ) {
                            composable(route = Screen.CityScreen.route) {
                                LocationScreen(navController = navController)
                            }
                            composable(
                                route = Screen.WeatherScreen.route + "/{city}",
                            ) {
                                WeatherScreen(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}