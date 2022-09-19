package com.scoqu.lasw.egasy.presentation.location.states

import com.scoqu.lasw.egasy.domain.model.City
import com.scoqu.lasw.egasy.domain.model.Weather

data class CityState(
    val cityList: MutableList<City> = mutableListOf(),
    val message: String = "",
    val loading: Boolean = false
)
