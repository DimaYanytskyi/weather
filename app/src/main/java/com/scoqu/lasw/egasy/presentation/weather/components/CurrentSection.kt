package com.scoqu.lasw.egasy.presentation.weather.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.google.gson.internal.bind.util.ISO8601Utils
import com.scoqu.lasw.egasy.domain.model.Weather
import com.scoqu.lasw.egasy.ui.theme.White
import java.text.ParsePosition
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@Composable
fun CurrentSection(
    weather: Weather,
    city: String
){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = city.uppercase(),
                    style = MaterialTheme.typography.h2,
                    color = Color(White.toArgb())
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = weather.currentTime.replace("T", " "),
                    style = MaterialTheme.typography.body1,
                    color = Color(White.toArgb())
                )
            }
            Text(
                text = "${weather.tempC}°",
                style = MaterialTheme.typography.h1,
                color = Color(White.toArgb())
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = weather.weather,
                style = MaterialTheme.typography.h5,
                color = Color(White.toArgb())
            )
            Text(
                text = "Day: ${weather.maxTempC}°   Night: ${weather.minTempC}°",
                style = MaterialTheme.typography.body1,
                color = Color(White.toArgb())
            )
        }
    }
}