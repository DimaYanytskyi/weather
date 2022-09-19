package com.scoqu.lasw.egasy.domain.use_case.weather

import com.scoqu.lasw.egasy.common.Resource
import com.scoqu.lasw.egasy.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherInteractor {

    fun getWeather(cityCountryCode : String, aggregateHours : String) : Flow<Resource<List<Weather>>>
    fun getLastWeather() : Flow<Resource<List<Weather>>>
    suspend fun insertWeather(weatherList: List<Weather>)
    suspend fun clearWeather()
}