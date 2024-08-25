package com.thiha.efficientweatherapp.domain

import com.thiha.efficientweatherapp.model.Weather
import com.thiha.efficientweatherapp.model.WeatherObject
import com.thiha.efficientweatherapp.utils.Constants.API_KEY
import com.thiha.efficientweatherapp.utils.Constants.APPID
import com.thiha.efficientweatherapp.utils.Constants.GET_FORECAST
import com.thiha.efficientweatherapp.utils.Constants.Q
import com.thiha.efficientweatherapp.utils.Constants.UNITS
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface WeatherApi {
    @GET(GET_FORECAST)
    suspend fun getWeather(
        @Query(Q) query: String,
        @Query(UNITS) units: String = "imperial",
        @Query(APPID) appid: String = API_KEY

    ): Weather


}