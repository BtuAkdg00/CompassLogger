package com.example.compasslogger.ui.state

import com.example.compasslogger.data.SensorReading

data class CompassUiState(
    val azimuth: Float = 0f,
    val directionLabel: String = "N",
    val sensorAvailable: Boolean = true,
    val recentReadings: List<SensorReading> = emptyList()
)