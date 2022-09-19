package com.scoqu.lasw.egasy.presentation.weather

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scoqu.lasw.egasy.R
import com.scoqu.lasw.egasy.common.Resource
import com.scoqu.lasw.egasy.domain.model.City
import com.scoqu.lasw.egasy.domain.use_case.weather.WeatherInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherInteractor: WeatherInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _weatherState = mutableStateOf(WeatherState())
    val weatherState: State<WeatherState> = _weatherState

    private val _dailyWeatherState = mutableStateOf(WeatherState())
    val dailyWeatherState: State<WeatherState> = _dailyWeatherState

    var city = City("-", "-")

    init {
        savedStateHandle.get<String>("city")?.let {
            city = City(it, "")
            getWeather(it)
            getDailyWeather(it)
        }
    }

    private fun getDailyWeather(cityCountryCode: String){
        viewModelScope.launch {
            weatherInteractor.getWeather(
                cityCountryCode,
                "24"
            ).collect { result ->
                when(result){
                    is Resource.Loading -> {
                        _dailyWeatherState.value = WeatherState(loading = true)
                    }
                    is Resource.Success -> {
                        _dailyWeatherState.value = WeatherState(weatherList = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _dailyWeatherState.value = WeatherState(message = result.message ?: "")
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getWeather(cityCountryCode: String){
        viewModelScope.launch {
            weatherInteractor.getWeather(
                cityCountryCode,
                "1"
            ).collect { result ->
                when(result){
                    is Resource.Loading -> {
                        _weatherState.value = WeatherState(loading = true)
                    }
                    is Resource.Success -> {
                        _weatherState.value = WeatherState(weatherList = result.data ?: emptyList())
                        weatherInteractor.insertWeather(_weatherState.value.weatherList)
                    }
                    is Resource.Error -> {
                        _weatherState.value = WeatherState(message = result.message ?: "")
                    }
                }
            }
        }
    }
}