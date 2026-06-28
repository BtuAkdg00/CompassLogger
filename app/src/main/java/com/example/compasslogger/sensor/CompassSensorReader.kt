package com.example.compasslogger.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.roundToInt

// Communicates with Android's sensor framework
// and passing compass updates back to the rest of the application.
class CompassSensorReader(
    context: Context,
    private val onAzimuthChanged: (Float) -> Unit,
    private val onSensorAvailabilityChanged: (Boolean) -> Unit
) : CompassSensor, SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val rotationVectorSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    override fun startListening() {
        if (rotationVectorSensor == null) {
            onSensorAvailabilityChanged(false)
            return
        }

        onSensorAvailabilityChanged(true)

        sensorManager.registerListener(
            this,
            rotationVectorSensor,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    override fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type != Sensor.TYPE_ROTATION_VECTOR) return

        val rotationMatrix = FloatArray(9)
        val orientationAngles = FloatArray(3)

        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
        SensorManager.getOrientation(rotationMatrix, orientationAngles)

        val azimuthInRadians = orientationAngles[0]
        val azimuthInDegrees = Math.toDegrees(azimuthInRadians.toDouble()).toFloat()

        // Keep the angle between 0° and 359° before sending it to the UI.
        val normalizedAzimuth = ((azimuthInDegrees % 360) + 360) % 360

        onAzimuthChanged(normalizedAzimuth.roundToInt().toFloat())
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}