package com.thiha.efficientweatherapp.widgets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SunriseSunsetIndicator(
    sunriseTime: LocalTime,
    sunsetTime: LocalTime, currentTime: LocalTime,
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