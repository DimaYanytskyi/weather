package com.scoqu.lasw.egasy.presentation.location

import androidx.compose.ui.focus.FocusState
import com.scoqu.lasw.egasy.domain.model.City

sealed class CityScreenEvent{
    data class EnteredCity(val value: String): CityScreenEvent()
    data class ChangeCityFocus(val focusState: FocusState): CityScreenEvent()
    data class SaveCity(val value: City): CityScreenEvent()
    data class DeleteCity(val value: City, val index: Int): CityScreenEvent()
}
