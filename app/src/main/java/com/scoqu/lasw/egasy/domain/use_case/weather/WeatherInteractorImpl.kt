package com.scoqu.lasw.egasy.domain.use_case.weather

import com.scoqu.lasw.egasy.common.Resource
import com.scoqu.lasw.egasy.data.remote.dto.weather.toWeatherList
import com.scoqu.lasw.egasy.domain.model.Weather
import com.scoqu.lasw.egasy.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherInteractorImpl @Inject constructor(
    private val repository: WeatherRepository
) : WeatherInteractor {

    override fun getWeather(
        cityCountryCode: String,
        aggregateHours : String
    ): Flow<Resource<List<Weather>>> = flow {
        try {
            emit(Resource.Loading())
            val weatherList = repository.getWeatherData(cityCountryCode, aggregateHours).toWeatherList()
            emit(Resource.Success(weatherList))
        } catch (e : Exception) {
            emit(Resource.Error(e.message ?: e.toString()))
        }
    }

    override fun getLastWeather(): Flow<Resource<List<Weather>>> = flow {
        try {
            emit(Resource.Loading())
            val weatherList = repository.getLastData()
            emit(Resource.Success(weatherList))
        } catch (e : Exception) {
            emit(Resource.Error(e.message ?: e.toString()))
        }
    }

    override suspend fun insertWeather(weatherList: List<Weather>) {
        repository.saveWeather(weatherList)
    }

    override suspend fun clearWeather() {
        repository.clearWeather()
    }
}