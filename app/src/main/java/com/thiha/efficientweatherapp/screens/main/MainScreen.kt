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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
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


@RequiresApi(Build.VERSION_CODES.O)
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

@SuppressLint("DefaultLocale")
@RequiresApi(Build.VERSION_CODES.O)
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

        val sunriseTime = LocalTime.of(6, 0)  // Example sunrise time
        val sunsetTime = LocalTime.of(18, 0)  // Example sunset time
        val currentTime = LocalTime.now()

        SunriseSunsetIndicator(
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime,
            currentTime = currentTime,
            modifier = Modifier.padding(16.dp)
        )
        WeatherDetailsCard(
            precipitation = "0.0 in",
            wind = "${data.list.first().wind.speed} mph",
            humidity = "${data.list.first().main.humidity} %",
            visibility = "${String.format("%.2f",data.list.first().visibility / 1609.34)} mi",
            uvIndex = "Lowest",
            pressure = "${(data.list.first().main.pressure * 0.02953).toInt()} inHg"
        )

    }

}

@Composable
fun WeatherStateImage(imageUrl: String) {
Image(painter = rememberAsyncImagePainter(model = imageUrl), contentDescription = "Icon image", modifier = Modifier.size(80.dp) )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SunriseSunsetIndicator(
    sunriseTime: LocalTime,
    sunsetTime: LocalTime,currentTime: LocalTime,
    modifier: Modifier = Modifier
) {
    val sunriseMinutes = sunriseTime.toSecondOfDay()
    val sunsetMinutes = sunsetTime.toSecondOfDay()
    val currentMinutes = currentTime.toSecondOfDay()

    // Calculate the fraction of the day passed between sunrise and sunset
    val dayFraction = (currentMinutes - sunriseMinutes).toFloat() / (sunsetMinutes - sunriseMinutes)

    // State to control the animation progress (0f = sunrise, 1f = current time)
  //  val animationProgress = remember { mutableStateOf(0f) }
    val animationProgress = remember { Animatable(0f) }
    // Animate the sun's movement from 0f to dayFraction
    LaunchedEffect(key1 = Unit) {
        animationProgress.animateTo( // Now resolved
            targetValue = dayFraction,
            animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
        )
    }

    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(100.dp)) {
        val lineWidth = this.size.width
        val lineHeight = this.size.height

        // Create a curved path using a cubic Bezier curve
        val curvedLinePath = Path().apply {
            moveTo(0f, lineHeight * 0.75f) // Start point at the bottom left
            cubicTo(
                lineWidth * 0.25f, lineHeight * 0.25f, // Control point 1
                lineWidth * 0.75f, lineHeight * 1.25f, // Control point 2
                lineWidth, lineHeight * 0.75f           // End point at the bottom right
            )}

        // Draw the dotted curve
        drawPath(
            path = curvedLinePath,
            color = Color.Yellow,
            style = Stroke(
                width = 5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        )

        // Calculate the animated position of the sun
        val indicatorX = lineWidth * animationProgress.value
        val indicatorY = curvedLinePath.getYForX(indicatorX, lineHeight * 0.95f, this.size)

        // Draw the sun (indicator) with animation
        drawCircle(
            color = Color.Yellow,
            radius = 12.dp.toPx(),
            center = Offset(indicatorX, indicatorY)
        )
    }
}

// Extension function to approximate Y position on a Bézier curve for a given X
fun Path.getYForX(x: Float, defaultY: Float, size: Size): Float {
    // Approximate the Y position on the curve at a given X using a simple sampling method.
    val step = 1f
    for (i in 0..1000) {
        val t = i / 1000f
        val sampledX = (1 - t) * (1 - t) * 0f + 2 * (1 - t) * t * 0.25f + t * t * 1f
        val sampledY = (1 - t) * (1 - t) * 0.75f + 2 * (1 - t) * t * 0.25f + t * t * 1.25f
        if (sampledX * size.width >= x) {
            return sampledY * size.height
        }
    }
    return defaultY
}

fun formatDataTime(timestamp : Int) : String {
    val sdf = SimpleDateFormat("hh:mm:aa")
    val date = Date(timestamp.toLong() * 1000)

    return  sdf.format(date)
}
@Composable
fun WeatherDetailsCard(
    precipitation: String,
    wind: String,
    humidity: String,
    visibility: String,
    uvIndex: String,
    pressure: String,
    backgroundColor: Color = Color(0xFF2196F3) // Use a color similar to the one in the screenshot
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "DETAILS",
                style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "Precipitation", style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onBackground))
                    Text(text = precipitation, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground))
                }
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "South wind", style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onBackground))
                    Text(text = wind, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "HUM", style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onBackground))
                    Text(text = humidity, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground))
                }
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "Visibility", style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onBackground))
                    Text(text = visibility, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "UV", style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onBackground))
                    Text(text = uvIndex, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground))
                }
                Column {
                    Text(text = "Pressure", style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onBackground))
                    Text(text = pressure, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground))
                }
            }
        }
    }
}
