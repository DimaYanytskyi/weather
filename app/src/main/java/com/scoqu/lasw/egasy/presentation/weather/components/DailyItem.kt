package com.scoqu.lasw.egasy.presentation.weather.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
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
import com.scoqu.lasw.egasy.ui.theme.Blue
import com.scoqu.lasw.egasy.ui.theme.Pink
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun DailyItem(
    weather: Weather,
    modifier: Modifier
) {
    val tz = TimeZone.getDefault()
    val cal = GregorianCalendar.getInstance(tz)
    val offsetInMillis = tz.getOffset(cal.timeInMillis)
    val time = SimpleDateFormat("HH").format(weather.time - offsetInMillis).toInt()

    Column(modifier = Modifier
        .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(color = Color.White, thickness = 0.5.dp)
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = SimpleDateFormat("EEE").format(weather.time)+":",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
            Row{
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
                            .width(25.dp)
                            .height(25.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = weather.minTempC + "°",
                    style = MaterialTheme.typography.body1,
                    color = Color.White,
                    modifier = modifier.padding(start = 8.dp)
                )
            }
            Row{
                Weather.icons["humidity"]?.let { painterResource(id = it) }?.let {
                    Image(
                        painter = it,
                        contentDescription = "humidity",
                        colorFilter = ColorFilter.tint(Color(Blue.toArgb())),
                        modifier = modifier
                            .width(25.dp)
                            .height(25.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = weather.humidity + "%",
                    style = MaterialTheme.typography.body1,
                    color = Color(Blue.toArgb()),
                    modifier = modifier.padding(start = 8.dp)
                )
            }
            Row{
                Weather.icons["wind"]?.let { painterResource(id = it) }?.let {
                    Image(
                        painter = it,
                        contentDescription = "wind",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = modifier
                            .width(25.dp)
                            .height(25.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = weather.windSpeedMPH + "m/s",
                    style = MaterialTheme.typography.body1,
                    color = Color.White,
                    modifier = modifier.padding(start = 8.dp)
                )
            }
            Text(
                text = weather.maxTempC + "°",
                style = MaterialTheme.typography.body1,
                color = Color(Pink.toArgb())
            )
        }
    }

}