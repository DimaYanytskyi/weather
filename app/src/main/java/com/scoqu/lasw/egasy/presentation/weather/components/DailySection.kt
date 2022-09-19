package com.scoqu.lasw.egasy.presentation.weather.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.scoqu.lasw.egasy.domain.model.Weather

@Composable
fun DailySection(
    weatherList : List<Weather>,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier.padding(horizontal = 16.dp).height(350.dp)) {
        items(weatherList) { weather ->
            DailyItem(weather = weather, modifier = Modifier)
        }
    }
}