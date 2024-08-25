package com.thiha.efficientweatherapp.screens.main

import android.annotation.SuppressLint
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.thiha.efficientweatherapp.data.DataOrException
import com.thiha.efficientweatherapp.model.Weather

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    showData(mainViewModel = mainViewModel)
}


@Composable
fun showData(mainViewModel: MainViewModel) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeather(city = "Yangon")
    }.value

    if (weatherData.loading == true){
        CircularProgressIndicator()
    } else if (weatherData.data != null){
        Text(text = "Main Screen ${weatherData.data!!.toString()}")
    }

}