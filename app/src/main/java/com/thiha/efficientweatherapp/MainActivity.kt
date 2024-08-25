package com.thiha.efficientweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.thiha.efficientweatherapp.navigation.WeatherNavigation
import com.thiha.efficientweatherapp.ui.theme.EfficientWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EfficientWeatherApp()
        }
    }
}



@Composable
fun EfficientWeatherApp() {
    EfficientWeatherAppTheme {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherNavigation()

            }

        }
    }

}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier,) {

       Text(
           text = "Hello $name!",
           style = TextStyle(color = Color.Blue),
           modifier = modifier
       )


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EfficientWeatherAppTheme {
    }
}