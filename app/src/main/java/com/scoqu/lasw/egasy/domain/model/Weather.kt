package com.scoqu.lasw.egasy.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scoqu.lasw.egasy.R

@Entity
class Weather(
    val currentTime : String,
    val icon : String,
    val time : Long,
    val tempC : String,
    val maxTempC: String,
    val minTempC: String,
    val humidity : String,
    val windSpeedMPH : String,
    val weather : String,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val icons = mapOf(
            "cloud" to R.drawable.cloud,
            "cloud_day" to R.drawable.cloud_day,
            "cloud_night" to R.drawable.cloud_night,
            "night" to R.drawable.night,
            "rain_day" to R.drawable.rain_day,
            "rain_night" to R.drawable.rain_night,
            "snow" to R.drawable.snow,
            "sunny" to R.drawable.sunny,
            "thunder" to R.drawable.thunder,
            "wind" to R.drawable.wind,
            "humidity" to R.drawable.humidity,
            "back" to R.drawable.back
        )
    }
}


