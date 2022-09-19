package com.scoqu.lasw.egasy.presentation.weather.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.scoqu.lasw.egasy.domain.model.Weather
import com.scoqu.lasw.egasy.ui.theme.Black40
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun HourlyItem(
    weather: Weather,
    modifier: Modifier,
    isActive: Boolean = false
) {
    val tz = TimeZone.getDefault()
    val cal = GregorianCalendar.getInstance(tz)
    val offsetInMillis = tz.getOffset(cal.timeInMillis)
    val time = SimpleDateFormat("HH").format(weather.time - offsetInMillis).toInt()

    Box(modifier = modifier
        .fillMaxHeight()
        .background(
            if (isActive)
                Color(Black40.toArgb())
            else
                Color.Transparent
        )
        .padding(8.dp)
    ){
        Column(
            modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = SimpleDateFormat("HH:mm").format(weather.time - offsetInMillis),
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
            (if(weather.weather == "Overcast") Weather.icons["cloud"]
            else if(weather.weather == "Partially cloudy" && time >= 22 && time <= 5) Weather.icons["cloud_night"]
            else if(weather.weather == "Partially cloudy" && time < 22 && time > 5) Weather.icons["cloud_day"]
            else if(weather.weather == "Clear" && time >= 22 && time <= 5) Weather.icons["night"]
            else if(weather.weather == "Clear" && time < 22 && time > 5) Weather.icons["sunny"]
            else Weather.icons["rain_day"])?.let {
                painterResource(id = it)
            }?.let {
                Image(
                    painter = it,
                    contentDescription = weather.icon,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = modifier
                        .width(36.dp)
                        .height(36.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = weather.tempC + "°",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
        }
    }
}