package com.scoqu.lasw.egasy.data.remote

import com.scoqu.lasw.egasy.data.remote.dto.city.CityDtoItem
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CityApiInterface {

    @Headers(
        "X-RapidAPI-Key: 246a728560mshd9abfc48e25cbf2p1f4364jsnbc1500b1c65b",
        "X-RapidAPI-Host: spott.p.rapidapi.com"
    )
    @GET("places/autocomplete")
    suspend fun getCities(
        @Query("type") type : String,
        @Query("q") q : String,
        @Query("skip") skip : Int,
        @Query("limit") limit : Int,
    ) : List<CityDtoItem>
}