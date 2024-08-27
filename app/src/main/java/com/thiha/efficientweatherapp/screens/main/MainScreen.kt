package com.thiha.efficientweatherapp.screens.main

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateTo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import java.time.LocalTime
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thiha.efficientweatherapp.R
import com.thiha.efficientweatherapp.model.WeatherItem
import com.thiha.efficientweatherapp.navigation.WeatherScreens
import com.thiha.efficientweatherapp.screens.setting.SettingsViewModel
import com.thiha.efficientweatherapp.utils.formatDate
import com.thiha.efficientweatherapp.widgets.SunriseSunsetIndicator
import com.thiha.efficientweatherapp.widgets.WeatherDetailsCard
import com.thiha.efficientweatherapp.widgets.WeatherForecastCard
import java.text.SimpleDateFormat
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
    val curCity: String = if (city!!.isBlank()) "Seattle" else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }

    if (unitFromDb.isNotEmpty()) {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"

        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeather(
                city = curCity,
                units = unit
            )
        }.value


        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            MainScaffold(weather = weatherData.data!!, navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                navController = navController,
                title = weather.city.name,
                country = weather.city.country,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }
            )
        },

        ) { innerPadding ->
        MainContent(data = weather, innerPadding)


    }


}

@SuppressLint("DefaultLocale")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainContent(data: Weather, innerPadding: PaddingValues) {
    val imageUrl =
        "https://openweathermap.org/img/wn/${data!!.list.first().weather.first().icon}@2x.png"
    val scrollState = rememberScrollState()
    val uniqueDateItems = data.list.distinctBy { formatDate(it.dt) }
    val sunriseTime = LocalTime.of(6, 0)  // Example sunrise time
    val sunsetTime = LocalTime.of(18, 0)  // Example sunset time
    val currentTime = LocalTime.now()
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = data.list.first().weather.first().main,
            modifier = Modifier.padding(top = 26.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = data.list.first().main.temp.toInt().toString(),
                style = TextStyle(
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            WeatherStateImage(imageUrl = imageUrl)

        }
        Text(
            text = "Feel like ${data.list.first().main.feels_like.toInt()}°",
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = "High ${data.list.first().main.temp_max.toInt()}°. Low ${data!!.list.first().main.temp_min.toInt()}°",
            style = TextStyle(fontSize = 12.sp)
        )



        SunriseSunsetIndicator(
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime,
            currentTime = currentTime,
            modifier = Modifier.padding(16.dp)
        )


        FiveDayForeCast(uniqueDateItems)

        WeatherDetailsCard(
            precipitation = "0.0 in",
            wind = "${data.list.first().wind.speed} mph",
            humidity = "${data.list.first().main.humidity} %",
            visibility = "${String.format("%.2f", data.list.first().visibility / 1609.34)} mi",
            uvIndex = "Lowest",
            pressure = "${(data.list.first().main.pressure * 0.02953).toInt()} inHg"
        )

    }

}

@Composable
private fun FiveDayForeCast(uniqueDateItems: List<WeatherItem>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary.copy(
                alpha = 0.1f
            )
        ),
        modifier = Modifier
            .padding(8.dp)

    ) {

        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.Start) {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(8.dp)) {
                Icon(
                    painter = painterResource(R.drawable.baseline_calendar_today_24),
                    contentDescription = "calender",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "5-day forecast",
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
                )
            }

            LazyRow {
                items(items = uniqueDateItems) { item: WeatherItem ->
                    WeatherForecastCard(weather = item)
                }
            }
        }

    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl),
        contentDescription = "Icon image",
        modifier = Modifier.size(80.dp)
    )
}




