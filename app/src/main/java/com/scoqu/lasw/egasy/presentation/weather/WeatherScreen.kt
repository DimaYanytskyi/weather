package com.scoqu.lasw.egasy.presentation.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.scoqu.lasw.egasy.domain.model.Weather
import com.scoqu.lasw.egasy.presentation.location.CityViewModel.Companion.DEFAULT_WEATHER
import com.scoqu.lasw.egasy.presentation.weather.components.CurrentSection
import com.scoqu.lasw.egasy.presentation.weather.components.DailySection
import com.scoqu.lasw.egasy.presentation.weather.components.HourlySection

@Composable
fun WeatherScreen(
    navController: NavController,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val weather = viewModel.weatherState.value
    val dailyWeather = viewModel.dailyWeatherState.value

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 32.dp)) {
        CurrentSection(
            weather = if(dailyWeather.weatherList.isNotEmpty()){ dailyWeather.weatherList[0] } else DEFAULT_WEATHER,
            city = viewModel.city.name
        )
        Spacer(modifier = Modifier.height(20.dp))
        HourlySection(weatherList = weather.weatherList, modifier = Modifier)
        Spacer(modifier = Modifier.height(20.dp))
        DailySection(weatherList = dailyWeather.weatherList, modifier = Modifier)
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Weather.icons["back"]?.let { painterResource(id = it) }
                ?.let {
                    Image(
                        painter = it,
                        contentDescription = "Back",
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            Text(
                text = "Back",
                style = MaterialTheme.typography.h5,
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp).clickable {
                    navController.popBackStack()
                }
            )
        }
    }
}