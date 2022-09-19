package com.scoqu.lasw.egasy.data.remote.dto.weather

data class CurrentConditions(
    val cloudcover: Any,
    val datetime: String,
    val dew: Double,
    val heatindex: Any,
    val humidity: Double,
    val icon: String,
    val moonphase: Double,
    val precip: Any,
    val sealevelpressure: Any,
    val snowdepth: Any,
    val stations: String,
    val sunrise: String,
    val sunset: String,
    val temp: Double,
    val visibility: Any,
    val wdir: Any,
    val wgust: Any,
    val windchill: Any,
    val wspd: Any
)