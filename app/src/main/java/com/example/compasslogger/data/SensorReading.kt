package com.example.compasslogger.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensor_readings")
data class SensorReading(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val azimuth: Float,

    val direction: String,

    val timestamp: Long
)