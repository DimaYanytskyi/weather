package com.scoqu.lasw.egasy.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    val name : String,
    val countryCode: String,
    @PrimaryKey val id: Int? = null
)
