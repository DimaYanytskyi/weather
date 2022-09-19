package com.scoqu.lasw.egasy.data.repository

import com.scoqu.lasw.egasy.data.local.AppDao
import com.scoqu.lasw.egasy.data.remote.WeatherApiInterface
import com.scoqu.lasw.egasy.data.remote.dto.weather.WeatherDto
import com.scoqu.lasw.egasy.domain.model.Weather
import com.scoqu.lasw.egasy.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api : WeatherApiInterface,
    private val db : AppDao
) : WeatherRepository{

    override suspend fun getWeatherData(
        cityCountryCode: String,
        aggregateHours : String
    ): WeatherDto {
        return api.getWeatherData(
            cityCountryCode = cityCountryCode,
            aggregateHours = aggregateHours,
            unitGroup = "metric",
            contentType = "json"
        )
    }

    override suspend fun getLastData(): List<Weather> {
        return db.getWeatherData()
    }

    override suspend fun saveWeather(weatherList: List<Weather>) {
        db.insertWeatherList(weatherList)
    }

    override suspend fun clearWeather() {
        db.deleteWeather()
    }
}