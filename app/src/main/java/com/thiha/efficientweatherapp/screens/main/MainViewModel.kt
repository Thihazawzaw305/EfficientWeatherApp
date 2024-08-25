package com.thiha.efficientweatherapp.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiha.efficientweatherapp.data.DataOrException
import com.thiha.efficientweatherapp.model.Weather
import com.thiha.efficientweatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel(){
  suspend fun getWeather(city : String) : DataOrException<Weather, Boolean, Exception> {
      return repository.getWeather(city =city)
  }

}
