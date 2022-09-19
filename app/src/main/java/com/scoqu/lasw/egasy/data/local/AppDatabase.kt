package com.scoqu.lasw.egasy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scoqu.lasw.egasy.domain.model.City
import com.scoqu.lasw.egasy.domain.model.Weather

@Database(
    entities = [City::class, Weather::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val appDao : AppDao
}