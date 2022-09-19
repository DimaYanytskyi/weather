package com.scoqu.lasw.egasy.data.local

import androidx.room.*
import com.scoqu.lasw.egasy.domain.model.City
import com.scoqu.lasw.egasy.domain.model.Weather

@Dao
interface AppDao {

    @Query("SELECT * FROM city")
    suspend fun getCities() : List<City>

    @Query("SELECT * FROM city WHERE id = :id")
    suspend fun getCityById(id: Int) : City?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCityList(list: List<City>)

    @Query("DELETE FROM city")
    suspend fun deleteCity()


    @Query("SELECT * FROM weather")
    suspend fun getWeatherData() : List<Weather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherList(list: List<Weather>)

    @Query("DELETE FROM weather")
    suspend fun deleteWeather()
}