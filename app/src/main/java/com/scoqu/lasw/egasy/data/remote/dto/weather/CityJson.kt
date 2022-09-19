package com.scoqu.lasw.egasy.data.remote.dto.weather

data class CityJson(
    val address: String,
    val alerts: Any,
    val currentConditions: CurrentConditions,
    val distance: Int,
    val id: String,
    val index: Int,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val stationContributions: Any,
    val time: Int,
    val tz: String,
    val values: List<Value>
)