package com.scoqu.lasw.egasy.presentation.util

sealed class Screen(val route: String) {
    object CityScreen: Screen("location_screen")
    object WeatherScreen: Screen("weather_screen")
}
