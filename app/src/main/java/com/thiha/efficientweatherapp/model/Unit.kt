package com.thiha.efficientweatherapp.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_tbl")
data class Unit(
    @PrimaryKey
    @ColumnInfo(name = "unit")
    val unit: String)