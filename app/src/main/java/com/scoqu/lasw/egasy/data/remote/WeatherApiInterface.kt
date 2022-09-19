package com.scoqu.lasw.egasy.data.remote

import com.scoqu.lasw.egasy.data.remote.dto.weather.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApiInterface {

    @Headers(
        "X-RapidAPI-Key: 39c877467cmsh7402d904d2b2bd3p1a1752jsn6c9084bc06b7",
        "X-RapidAPI-Host: visual-crossing-weather.p.rapidapi.com"
    )
    @GET("forecast")
    suspend fun getWeatherData(
        @Query("location") cityCountryCode : String,
        @Query("aggregateHours") aggregateHours : String,
        @Query("unitGroup") unitGroup : String,
        @Query("contentType") contentType : String,
    ) : WeatherDto
}