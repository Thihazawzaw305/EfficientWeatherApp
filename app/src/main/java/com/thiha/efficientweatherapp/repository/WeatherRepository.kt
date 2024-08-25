package com.thiha.efficientweatherapp.repository

import com.thiha.efficientweatherapp.data.DataOrException
import com.thiha.efficientweatherapp.domain.WeatherApi
import com.thiha.efficientweatherapp.model.Weather
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api : WeatherApi) {
    suspend fun  getWeather(city : String) : DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = city)

        } catch (e : Exception){
            return  DataOrException(e = e)
        }
        return DataOrException(data = response)

    }
}