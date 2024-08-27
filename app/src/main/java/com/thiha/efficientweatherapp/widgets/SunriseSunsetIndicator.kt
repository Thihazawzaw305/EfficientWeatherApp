package com.thiha.efficientweatherapp.widgets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SunriseSunsetIndicator(
    sunriseTime: String,
    sunsetTime: String,
    modifier: Modifier = Modifier
) {

    // Animatable value for the sun's progress along the dotted line
    val animationProgress = remember { Animatable(0f) }

    // Launch the animation when the composable is first displayed
    LaunchedEffect(key1 = Unit) {
        animationProgress.animateTo(
            targetValue = 1f, // Move from start (0f) to end (1f)
            animationSpec = tween(durationMillis = 4000, easing = LinearEasing)
        )
    }

    // Define the card that contains everything
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary.copy(
                alpha = 0.1f
            )
        ),
    ) {
        // Canvas to draw the dotted line and sun
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 12.dp)
        ) {
            val canvasWidth = size.width
            val lineY = size.height / 2

            // Draw a straight dotted line
            drawLine(
                color = Color(0xFFFF9800),
                start = Offset(0f, lineY),
                end = Offset(canvasWidth, lineY),
                strokeWidth = 4.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )

            // Calculate the position of the sun based on the animation progress
            val sunX = canvasWidth * animationProgress.value

            // Draw the sun (indicator) with animation; disappears if at end
            if (animationProgress.value < 1f) {
                drawCircle(
                    color = Color(0xFFFF9800),
                    radius = 12.dp.toPx(),
                    center = Offset(sunX, lineY)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                text = "Sunrise ${sunriseTime}",
                fontSize = 12.sp,

                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier)

            Text(
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                text = "Sunset ${sunsetTime}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = androidx.compose.ui.text.style.TextAlign.End // Align to the end
            )
        }

    }
}
