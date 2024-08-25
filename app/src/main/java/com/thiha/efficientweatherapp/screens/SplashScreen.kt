package com.thiha.efficientweatherapp.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thiha.efficientweatherapp.R
import com.thiha.efficientweatherapp.navigation.WeatherScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(initialValue = 0f)
    }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = { OvershootInterpolator(7f).getInterpolation(it) })
        )

delay(1500)

        navController.navigate(WeatherScreens.MainScreen.name){
            popUpTo(WeatherScreens.SplashScreen.name){
                inclusive = true
            }
        }
    })


    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(430.dp)
            .scale(scale.value),

        shape = CircleShape,
        color = MaterialTheme.colorScheme.background,
      //  border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onBackground)
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.cloudy),
                contentDescription = "Cloudy Icon",
                modifier = Modifier.size(95.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = "Your Efficient Weather!", style = MaterialTheme.typography.titleMedium)

        }
    }
}