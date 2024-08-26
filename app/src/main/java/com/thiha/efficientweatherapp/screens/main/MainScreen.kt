package com.thiha.efficientweatherapp.screens.main

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
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
    Scaffold(
        topBar = {
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
    val imageUrl = "https://openweathermap.org/img/wn/${data!!.list.first().weather.first().icon}.png"
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = data.list.first().weather.first().main, modifier = Modifier.padding(top = 26.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(text = data!!.list.first().main.temp.toInt().toString(), style = TextStyle(fontSize = 100.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground))
            WeatherStateImage(imageUrl = imageUrl)

        }
        Text(text = "Feel like ${data!!.list.first().main.feels_like.toInt()}°", style = TextStyle(fontSize = 18.sp), modifier = Modifier.padding(bottom = 12.dp))
        Text(text = "High ${data!!.list.first().main.temp_max.toInt()}°. Low ${data!!.list.first().main.temp_min.toInt()}°", style = TextStyle(fontSize = 12.sp))



    }

}

@Composable
fun WeatherStateImage(imageUrl: String) {
Image(painter = rememberAsyncImagePainter(model = imageUrl), contentDescription = "Icon image", modifier = Modifier.size(80.dp) )
}
