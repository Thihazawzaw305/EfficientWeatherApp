package com.thiha.efficientweatherapp.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

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
            .padding(8.dp)
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
