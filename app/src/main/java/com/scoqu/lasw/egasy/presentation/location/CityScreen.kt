package com.scoqu.lasw.egasy.presentation.location

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.scoqu.lasw.egasy.R
import com.scoqu.lasw.egasy.presentation.location.components.CityItem
import com.scoqu.lasw.egasy.presentation.location.components.SearchInput
import com.scoqu.lasw.egasy.presentation.location.components.SearchListItem
import com.scoqu.lasw.egasy.presentation.util.Screen
import com.scoqu.lasw.egasy.ui.theme.White

@Composable
fun LocationScreen(
    navController: NavController,
    viewModel: CityViewModel = hiltViewModel()
) {
    val cityState = viewModel.cityState.value

    val weatherState by remember {
        viewModel.weatherState
    }
    val textFieldState = viewModel.cityInput.value
    val searchState = viewModel.searchState.value

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h1,
            color = Color(White.toArgb())
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchInput(
                text = textFieldState.text,
                hint = textFieldState.hint,
                onValueChange = {
                    viewModel.onEvent(CityScreenEvent.EnteredCity(it))
                },
                onFocusChange = {
                    viewModel.onEvent(CityScreenEvent.ChangeCityFocus(it))
                },
                isHintVisible = textFieldState.isHintVisible,
                singleLine = false
            )
            if (searchState.message.isNotBlank()) {
                Text(
                    text = searchState.message,
                    style = MaterialTheme.typography.h5,
                    color = White
                )
            } else if (searchState.loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                searchState.cityList.forEach { city ->
                    SearchListItem(
                        modifier = Modifier
                            .clickable {
                                viewModel.onEvent(CityScreenEvent.SaveCity(city))
                            },
                        city = city
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    if (cityState.cityList.isNotEmpty()) {
                    itemsIndexed(
                        items = cityState.cityList,
                        key = { _,listItem ->
                            listItem.hashCode()
                        }
                    ) { index, item ->
                        CityItem(
                            city = item,
                            weather = if (weatherState.weatherList.isEmpty() || weatherState.weatherList[index] == CityViewModel.DEFAULT_WEATHER) {
                                CityViewModel.DEFAULT_WEATHER
                            } else {
                                weatherState.weatherList[index]
                            },
                            modifier = Modifier.padding(vertical = 8.dp).clickable {
                                navController.navigate(Screen.WeatherScreen.route + "/${item.name.lowercase()}")
                            },
                            onDismiss = {
                                viewModel.onEvent(CityScreenEvent.DeleteCity(item, index))
                            }
                        )
                    }
                }
            }
        }
    }
}