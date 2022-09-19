package com.scoqu.lasw.egasy.domain.use_case.city

import com.scoqu.lasw.egasy.common.Resource
import com.scoqu.lasw.egasy.data.remote.dto.city.toCity
import com.scoqu.lasw.egasy.domain.model.City
import com.scoqu.lasw.egasy.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CityInteractorImpl @Inject constructor(
    private val repository: CityRepository
) : CityInteractor {

    override fun getCity(prefix: String): Flow<Resource<List<City>>> = flow {
        try {
            emit(Resource.Loading())
            val cityList = repository.getCities(prefix).map { it.toCity() }
            emit(Resource.Success(cityList))
        } catch (e : Exception){
            emit(Resource.Error(e.message ?: e.toString()))
        }
    }

    override fun getLoadedCities(): Flow<Resource<List<City>>> = flow {
        try {
            emit(Resource.Loading())
            val cityList = repository.getLoadedCities()
            emit(Resource.Success(cityList))
        } catch (e : Exception){
            emit(Resource.Error(e.message ?: e.toString()))
        }
    }

    override suspend fun getCityById(id: Int): City? {
        return repository.getCityById(id)
    }

    override suspend fun insertCities(cityList: List<City>) {
        repository.saveCities(cityList)
    }

    override suspend fun clearCities() {
        repository.clearCities()
    }
}