package com.example.compasslogger.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compasslogger.data.SensorRepository

class CompassViewModelFactory(
    private val repository: SensorRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompassViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CompassViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}