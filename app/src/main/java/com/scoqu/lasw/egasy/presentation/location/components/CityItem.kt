package com.scoqu.lasw.egasy.presentation.location.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.gson.internal.bind.util.ISO8601Utils
import com.scoqu.lasw.egasy.domain.model.City
import com.scoqu.lasw.egasy.domain.model.Weather
import com.scoqu.lasw.egasy.presentation.location.CityScreenEvent
import com.scoqu.lasw.egasy.presentation.location.CityViewModel
import com.scoqu.lasw.egasy.presentation.util.Screen
import com.scoqu.lasw.egasy.ui.theme.Green
import com.scoqu.lasw.egasy.ui.theme.GreenTransparent
import com.scoqu.lasw.egasy.ui.theme.White
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("SimpleDateFormat")
@Composable
fun CityItem(
    city: City,
    weather: Weather,
    modifier: Modifier,
    onDismiss: () -> Unit
) {
   Box(
       modifier = modifier
           .fillMaxWidth()
           .clip(RoundedCornerShape(10.dp))
           .background(
               brush = Brush.verticalGradient(
                   colors = listOf(
                       Green,
                       GreenTransparent
                   )
               )
           )
   ) {
       val dismissState = rememberDismissState(
           confirmStateChange = {
               if(it == DismissValue.DismissedToStart){
                   onDismiss()
               }
               true
           }
       )

       if (dismissState.isDismissed(DismissDirection.EndToStart)) {
           onDismiss()
       }

       SwipeToDismiss(
           state = dismissState,
           modifier = Modifier.padding(vertical = 8.dp),
           directions = setOf(
               DismissDirection.EndToStart
           ),
           dismissThresholds = { direction ->
               FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.2f else 0.1f)
           },
           background = {
               val color by animateColorAsState(
                   when (dismissState.targetValue) {
                       DismissValue.Default -> Color.Transparent
                       else -> Color.Transparent
                   }
               )
               val alignment = Alignment.CenterEnd
               val icon = Icons.Default.Delete

               val scale by animateFloatAsState(
                   if (dismissState.targetValue == DismissValue.Default) 0f else 1f
               )

               Box(
                   Modifier
                       .fillMaxSize()
                       .background(color)
                       .padding(horizontal = Dp(20f)),
                   contentAlignment = alignment
               ) {
                   Icon(
                       icon,
                       contentDescription = "Delete Icon",
                       modifier = Modifier.scale(scale)
                   )
               }
           },
           dismissContent = {
               Column(modifier = Modifier
                   .fillMaxWidth()
                   .padding(8.dp)) {
                   Row(
                       modifier = Modifier
                           .fillMaxWidth(),
                       horizontalArrangement = Arrangement.SpaceBetween
                   ) {
                       Column {
                           Text(
                               text = city.name.uppercase() + ", " + city.countryCode.uppercase(),
                               style = MaterialTheme.typography.h5,
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
                           style = MaterialTheme.typography.h2,
                           color = Color(White.toArgb())
                       )
                   }
                   Spacer(modifier = Modifier.height(8.dp))
                   Row(
                       modifier = Modifier
                           .fillMaxWidth(),
                       horizontalArrangement = Arrangement.SpaceBetween
                   ) {
                       Text(
                           text = weather.weather,
                           style = MaterialTheme.typography.body1,
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
       )

   }
}