package com.thiha.efficientweatherapp.screens.main

import android.renderscript.Sampler.Value
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.thiha.efficientweatherapp.data.DataOrException
import com.thiha.efficientweatherapp.model.Weather
import com.thiha.efficientweatherapp.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeather(city = "Yangon")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController)
    }
}


@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController,
            title = "Yangon"
        )
    },

        ) { innerPadding ->
        MainContent(data = weather, innerPadding)


    }


}

@Composable
fun MainContent(data: Weather, innerPadding: PaddingValues) {
    Column(modifier = Modifier.padding(innerPadding)) {

    }

}