package com.example.compasslogger.data

import android.util.Log
import kotlinx.coroutines.flow.Flow


class SensorRepository(
    private val sensorReadingDao: SensorReadingDao
) {

    fun getRecentReadings(): Flow<List<SensorReading>> {
        return sensorReadingDao.getRecentReadings()
    }

    suspend fun saveReading(
        azimuth: Float,
        direction: String,
        timestamp: Long
    ) {

        // Create a database object from the current sensor values
        val reading = SensorReading(
            azimuth = azimuth,
            direction = direction,
            timestamp = timestamp
        )

        sensorReadingDao.insertReading(reading)

        // Logcat testing
        Log.d(
            "CompassRepository",
            "Saved reading: ${reading.azimuth}°, ${reading.direction}"
        )
    }
}