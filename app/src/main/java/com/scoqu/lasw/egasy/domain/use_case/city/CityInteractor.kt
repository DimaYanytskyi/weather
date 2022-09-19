package com.scoqu.lasw.egasy.domain.use_case.city

import com.scoqu.lasw.egasy.common.Resource
import com.scoqu.lasw.egasy.domain.model.City
import kotlinx.coroutines.flow.Flow

interface CityInteractor {

    fun getCity(prefix : String) : Flow<Resource<List<City>>>
    fun getLoadedCities() : Flow<Resource<List<City>>>
    suspend fun getCityById(id: Int) : City?
    suspend fun insertCities(cityList: List<City>)
    suspend fun clearCities()
}