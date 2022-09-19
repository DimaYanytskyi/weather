package com.scoqu.lasw.egasy.domain.repository

import com.scoqu.lasw.egasy.data.remote.dto.city.CityDtoItem
import com.scoqu.lasw.egasy.domain.model.City

interface CityRepository {

    suspend fun getCities(prefix: String) : List<CityDtoItem>

    suspend fun getLoadedCities() : List<City>

    suspend fun getCityById(id: Int) : City?

    suspend fun saveCities(cityList: List<City>)

    suspend fun clearCities()
}