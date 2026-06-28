package com.example.compasslogger.domain

object CompassDirection {

    fun fromAzimuth(azimuth: Float): String {

        // Keeps the angle between 0° and 359° in case the sensor
        val normalizedAzimuth = ((azimuth % 360) + 360) % 360

        // Convert the angle into one of the eight compass directions.
        return when {
            normalizedAzimuth >= 337.5 || normalizedAzimuth < 22.5 -> "N"
            normalizedAzimuth < 67.5 -> "NE"
            normalizedAzimuth < 112.5 -> "E"
            normalizedAzimuth < 157.5 -> "SE"
            normalizedAzimuth < 202.5 -> "S"
            normalizedAzimuth < 247.5 -> "SW"
            normalizedAzimuth < 292.5 -> "W"
            else -> "NW"
        }
    }
}