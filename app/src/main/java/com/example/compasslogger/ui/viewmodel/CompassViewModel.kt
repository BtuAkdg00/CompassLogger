package com.example.compasslogger.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compasslogger.data.SensorRepository
import com.example.compasslogger.domain.CompassDirection
import com.example.compasslogger.ui.state.CompassUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

class CompassViewModel(
    private val repository: SensorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        CompassUiState(
            azimuth = 0f,
            directionLabel = CompassDirection.fromAzimuth(0f),
            sensorAvailable = true
        )
    )

    // Expose a read-only version so the UI can't modify the state directly.
    val uiState: StateFlow<CompassUiState> = _uiState.asStateFlow()

    init {

        viewModelScope.launch {
            repository.getRecentReadings().collectLatest { readings ->
                _uiState.value = _uiState.value.copy(
                    recentReadings = readings
                )
            }
        }
    }

    fun updateAzimuth(azimuth: Float) {
        val previousAzimuth = _uiState.value.azimuth

        // Ignores small changes to reduce unnecessary updates.
        if (abs(azimuth - previousAzimuth) < 5f) {
            return
        }

        Log.d("CompassLogger", "Azimuth updated: $azimuth")

        _uiState.value = _uiState.value.copy(
            azimuth = azimuth,
            directionLabel = CompassDirection.fromAzimuth(azimuth)
        )
    }

    fun updateSensorAvailability(isAvailable: Boolean) {
        _uiState.value = _uiState.value.copy(
            sensorAvailable = isAvailable
        )
    }

    fun saveCurrentReading() {
        val currentState = _uiState.value

        viewModelScope.launch {
            repository.saveReading(
                azimuth = currentState.azimuth,
                direction = currentState.directionLabel,
                timestamp = System.currentTimeMillis()
            )
        }
    }
}