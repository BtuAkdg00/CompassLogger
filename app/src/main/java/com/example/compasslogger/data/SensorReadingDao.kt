package com.example.compasslogger.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SensorReadingDao {

    // Inserts a single sensor reading into the local database.
    @Insert
    suspend fun insertReading(reading: SensorReading)

    // Returns the five most recent readings so they can be displayed in the UI.
    @Query("SELECT * FROM sensor_readings ORDER BY timestamp DESC LIMIT 5")
    fun getRecentReadings(): Flow<List<SensorReading>>
}