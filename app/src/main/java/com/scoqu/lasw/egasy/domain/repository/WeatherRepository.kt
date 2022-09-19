package com.scoqu.lasw.egasy.domain.repository

import com.scoqu.lasw.egasy.data.remote.dto.weather.WeatherDto
import com.scoqu.lasw.egasy.domain.model.Weather

interface WeatherRepository {

    suspend fun getWeatherData(
        cityCountryCode: String,
        aggregateHours : String
    ) : WeatherDto

    suspend fun getLastData() : List<Weather>

    suspend fun saveWeather(weatherList: List<Weather>)

    suspend fun clearWeather()
}