package com.scoqu.lasw.egasy.presentation.location

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scoqu.lasw.egasy.common.Resource
import com.scoqu.lasw.egasy.domain.model.City
import com.scoqu.lasw.egasy.domain.model.Weather
import com.scoqu.lasw.egasy.domain.use_case.city.CityInteractor
import com.scoqu.lasw.egasy.domain.use_case.weather.WeatherInteractor
import com.scoqu.lasw.egasy.presentation.location.states.CityState
import com.scoqu.lasw.egasy.presentation.location.states.SearchState
import com.scoqu.lasw.egasy.presentation.location.states.TextFieldState
import com.scoqu.lasw.egasy.presentation.location.states.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val weatherInteractor: WeatherInteractor,
    private val cityInteractor: CityInteractor
) : ViewModel() {

    private val _cityInput = mutableStateOf(
        TextFieldState(
            hint = "Search for a city"
        )
    )
    val cityInput: State<TextFieldState> = _cityInput

    private val _cityState = mutableStateOf(CityState())
    val cityState: State<CityState> = _cityState

    private val _weatherState = mutableStateOf(WeatherState())
    val weatherState: State<WeatherState> = _weatherState

    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState

    private var getSearchJob: Job? = null


    init {
        getCitiesAndWeather()
    }

    fun onEvent(event: CityScreenEvent){
        when(event) {
            is CityScreenEvent.EnteredCity -> {
                _cityInput.value = _cityInput.value.copy(
                    text = event.value
                )
                if(_cityInput.value.text.length >= 3){
                    getCityByPrefix(_cityInput.value.text)
                }
            }
            is CityScreenEvent.ChangeCityFocus -> {
                _cityInput.value = _cityInput.value.copy(
                    isHintVisible = !event.focusState.isFocused && cityInput.value.text.isBlank()
                )
            }
            is CityScreenEvent.SaveCity -> {
                viewModelScope.launch {
                    val newList : MutableList<City> = _cityState.value.cityList.toMutableList()
                    if(newList.contains(event.value)){

                    } else {
                        newList.add(event.value)
                        getWeatherFromInternet(event.value.name)
                        _cityState.value = CityState(cityList = newList)
                    }
                    _searchState.value = SearchState()
                    cityInteractor.clearCities()
                    cityInteractor.insertCities(_cityState.value.cityList)
                }
            }
            is CityScreenEvent.DeleteCity -> {
                    val newList : MutableList<City> = _cityState.value.cityList.toMutableList()
                    newList.remove(event.value)
                    val newWeatherList : MutableList<Weather> = _weatherState.value.weatherList.toMutableList()
                    if(newWeatherList.isNotEmpty()) newWeatherList.removeAt(event.index)
                    if(newList.isEmpty() || newWeatherList.isEmpty()) {
                        _cityState.value = CityState(cityList = mutableListOf())
                        _weatherState.value = WeatherState(weatherList = emptyList())
                    } else {
                        _cityState.value = CityState(cityList = newList)
                        _weatherState.value = WeatherState(weatherList = newWeatherList.toList())
                    }
            }
        }
    }

    private fun getCityByPrefix(prefix: String){
        getSearchJob?.cancel()
        getSearchJob = viewModelScope.launch {
            cityInteractor.getCity(prefix).collectLatest {
                when(it){
                    is Resource.Loading -> {
                        _searchState.value = SearchState(loading = true)
                    }
                    is Resource.Success -> {
                        _searchState.value = SearchState(cityList = it.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _searchState.value = SearchState(message = it.message ?: "")
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCitiesAndWeather(){
        viewModelScope.launch {
            cityInteractor.getLoadedCities().collect {
                when(it){
                    is Resource.Loading -> {
                        _cityState.value = CityState(loading = true)
                    }
                    is Resource.Success -> {
                        _cityState.value = CityState(cityList = it.data?.toMutableList() ?: mutableListOf())
                        _cityState.value.cityList.forEach { city ->
                            getWeatherFromInternet(city.name)
                        }
                    }
                    is Resource.Error -> {
                        _cityState.value = CityState(message = it.message ?: "")
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private suspend fun getWeatherFromInternet(city: String){
        weatherInteractor.getWeather(
            city.lowercase(),
            "24",
        ).collect { result ->
            when(result){
                is Resource.Loading -> {
                    _weatherState.value = _weatherState.value.copy(
                        loading = true
                    )
                }
                is Resource.Success -> {
                    val newList : MutableList<Weather> = _weatherState.value.weatherList.toMutableList()
                    if(result.data.isNullOrEmpty()){
                        newList.add(DEFAULT_WEATHER)
                    } else {
                        result.data.map { newList.add(it) }
                    }
                    _weatherState.value = _weatherState.value.copy(
                        weatherList = newList
                    )
                }
                is Resource.Error -> {
                    _weatherState.value = _weatherState.value.copy(
                        message = result.message ?: ""
                    )
                }
            }
        }
    }

    companion object {
        val DEFAULT_WEATHER = Weather("-","-", System.currentTimeMillis(),"-", "-", "-", "-", "-", "-")
    }
}