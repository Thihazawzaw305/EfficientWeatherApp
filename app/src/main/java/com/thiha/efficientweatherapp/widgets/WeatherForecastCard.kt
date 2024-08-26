package com.thiha.efficientweatherapp.widgets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.thiha.efficientweatherapp.model.WeatherItem
import com.thiha.efficientweatherapp.utils.formatDate

@Composable
fun WeatherForecastCard(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather.first().icon}.png"

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary.copy(
                alpha = 0.1f
            )
        ),
        modifier = Modifier
            .padding(8.dp)
            .height(180.dp)

    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = "${weather.main.temp_max.toInt()}°",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${weather.main.temp_min.toInt()}°",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 12.sp,
            )
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "Icon image",
                modifier = Modifier.size(50.dp)
            )

            Text(
                text = weather?.rain?.`3h`?.toString()?.plus("%") ?: "0%",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 10.sp
            )
            Text(
                text = formatDate(weather.dt).split(",").first(),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp
            )
            Log.d("DT","${weather.dt.toString()}")
        }
    }

}