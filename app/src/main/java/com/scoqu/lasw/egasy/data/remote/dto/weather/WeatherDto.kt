package com.scoqu.lasw.egasy.data.remote.dto.weather

import com.scoqu.lasw.egasy.domain.model.Weather

data class WeatherDto(
    val locations: Map<String, CityJson>,
    val messages: Any
)

fun WeatherDto.toWeatherList() : List<Weather> {
    val list = mutableListOf<Weather>()
    locations.values.map { city ->
        city.values.map {
            list.add(
                Weather(
                    city.currentConditions.datetime,
                    city.currentConditions.icon,
                    it.datetime,
                    it.temp.toString(),
                    it.maxt.toString(),
                    it.mint.toString(),
                    it.humidity.toString(),
                    it.wspd.toString(),
                    it.conditions,
                )
            )
        }
    }
    return list
}