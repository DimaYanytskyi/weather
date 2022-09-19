package com.scoqu.lasw.egasy.data.remote.dto.city

import com.scoqu.lasw.egasy.domain.model.City

data class CityDtoItem(
    val country: Country,
    val elevation: Int,
    val geonameId: Int,
    val id: String,
    val name: String,
    val population: Int,
    val timezoneId: String,
    val type: String
)

fun CityDtoItem.toCity() : City {
    return City(
        name,
        country.id
    )
}