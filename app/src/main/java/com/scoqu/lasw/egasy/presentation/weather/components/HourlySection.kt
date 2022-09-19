package com.scoqu.lasw.egasy.presentation.weather.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.scoqu.lasw.egasy.domain.model.Weather
import com.scoqu.lasw.egasy.ui.theme.Green
import com.scoqu.lasw.egasy.ui.theme.GreenTransparent

@Composable
fun HourlySection(
    weatherList : List<Weather>,
    modifier: Modifier
) {
    LazyRow(modifier = modifier
        .height(100.dp)
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Green,
                    GreenTransparent
                )
            )
        )
        .padding(start = 16.dp)
    ) {
        items(weatherList) { weather ->
            HourlyItem(weather = weather, modifier = modifier, weatherList.first() == weather)
        }
    }
}