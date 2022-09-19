package com.scoqu.lasw.egasy.presentation.weather

import com.scoqu.lasw.egasy.domain.model.Weather

data class WeatherState(
    val weatherList: List<Weather> = emptyList(),
    val message: String = "",
    val loading: Boolean = false
)
