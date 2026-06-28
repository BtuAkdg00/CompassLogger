package com.example.compasslogger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.compasslogger.data.CompassDatabase
import com.example.compasslogger.data.SensorRepository
import com.example.compasslogger.sensor.CompassSensor
import com.example.compasslogger.sensor.CompassSensorReader
import com.example.compasslogger.ui.screens.CompassScreen
import com.example.compasslogger.ui.theme.CompassLoggerTheme
import com.example.compasslogger.ui.viewmodel.CompassViewModel
import com.example.compasslogger.ui.viewmodel.CompassViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var compassSensor: CompassSensor

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            CompassDatabase::class.java,
            "compass_database"
        ).build()
    }

    private val repository by lazy {
        SensorRepository(database.sensorReadingDao())
    }

    private val viewModel: CompassViewModel by viewModels {
        CompassViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        compassSensor = CompassSensorReader(
            context = this,
            onAzimuthChanged = viewModel::updateAzimuth,
            onSensorAvailabilityChanged = viewModel::updateSensorAvailability
        )

        setContent {
            CompassLoggerTheme {
                val uiState by viewModel.uiState.collectAsState()

                DisposableEffect(Unit) {
                    compassSensor.startListening()

                    onDispose {
                        compassSensor.stopListening()
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CompassScreen(
                        uiState = uiState,
                        onSaveReading = viewModel::saveCurrentReading,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}