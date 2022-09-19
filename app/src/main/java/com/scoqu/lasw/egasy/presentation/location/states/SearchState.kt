package com.scoqu.lasw.egasy.presentation.location.states

import com.scoqu.lasw.egasy.domain.model.City

data class SearchState(
    val cityList: List<City> = emptyList(),
    val message: String = "",
    val loading: Boolean = false
)
