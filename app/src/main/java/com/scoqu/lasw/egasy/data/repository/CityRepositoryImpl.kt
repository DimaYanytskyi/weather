package com.scoqu.lasw.egasy.data.repository

import com.scoqu.lasw.egasy.data.local.AppDao
import com.scoqu.lasw.egasy.data.remote.CityApiInterface
import com.scoqu.lasw.egasy.data.remote.dto.city.CityDtoItem
import com.scoqu.lasw.egasy.domain.model.City
import com.scoqu.lasw.egasy.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val api : CityApiInterface,
    private val db : AppDao
) : CityRepository {

    override suspend fun getCities(prefix: String): List<CityDtoItem> {
        return api.getCities(
            "CITY",
            prefix,
            0,
            5
        )
    }

    override suspend fun getLoadedCities(): List<City> {
        return db.getCities()
    }

    override suspend fun getCityById(id: Int): City? {
        return db.getCityById(id)
    }

    override suspend fun saveCities(cityList: List<City>) {
        db.insertCityList(cityList)
    }

    override suspend fun clearCities() {
        db.deleteCity()
    }
}