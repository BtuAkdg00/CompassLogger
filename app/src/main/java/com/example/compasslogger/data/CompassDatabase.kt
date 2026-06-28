package com.example.compasslogger.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SensorReading::class],
    version = 1
)
abstract class CompassDatabase : RoomDatabase() {

    abstract fun sensorReadingDao(): SensorReadingDao
}