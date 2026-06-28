package com.example.compasslogger.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compasslogger.ui.state.CompassUiState

@Composable
fun CompassScreen(
    uiState: CompassUiState,
    onSaveReading: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Compass Logger",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Current heading:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 32.dp)
        )

        Text(
            text = "%.1f°".format(uiState.azimuth),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = "Direction: ${uiState.directionLabel}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        if (!uiState.sensorAvailable) {
            Text(
                text = "No compatible compass sensor found.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Button(
            onClick = onSaveReading,
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text("Save reading")
        }
        Text(
            text = "Recent readings",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 32.dp)
        )

        uiState.recentReadings.forEach { reading ->
            Text(
                text = "${reading.azimuth}° ${reading.direction}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}