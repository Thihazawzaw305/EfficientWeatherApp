package com.thiha.efficientweatherapp.screens.main

import androidx.lifecycle.ViewModel
import com.thiha.efficientweatherapp.data.DataOrException
import com.thiha.efficientweatherapp.model.Weather
import com.thiha.efficientweatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel(){
  suspend fun getWeather(city : String, units : String) : DataOrException<Weather, Boolean, Exception> {
      return repository.getWeather(city =city, units = units)
  }

}
