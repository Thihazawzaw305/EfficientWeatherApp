package com.thiha.efficientweatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thiha.efficientweatherapp.model.Favorite
import com.thiha.efficientweatherapp.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 3, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}