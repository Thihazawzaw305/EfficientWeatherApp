package com.thiha.efficientweatherapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//fun formatDate(timestamp : Int ) : String {
//    val sdf = SimpleDateFormat("EEE, MMM d")
//    val date = Date(timestamp.toLong())
//
//    return sdf.format(date)
//}
fun formatDate(timestamp: Int): String {
    // Multiply by 1000 because Date takes milliseconds
    val date = Date(timestamp.toLong() * 1000)
    val format = SimpleDateFormat("E, MMM d", Locale.ENGLISH)
    return format.format(date)
}
fun formatDataTime(timestamp : Int) : String {
    val sdf = SimpleDateFormat("hh:mm:aa")
    val date = Date(timestamp.toLong() * 1000)

    return  sdf.format(date)
}