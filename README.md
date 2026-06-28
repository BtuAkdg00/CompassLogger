# Compass Logger

## Introduction

The application reads the device's compass direction using Android's `SensorManager`, displays the current heading, 
allows the user to save readings locally using Room, and displays the five most recent saved readings.
Before starting this assignment, I had very limited experience with native Android development.
Because of that, I tried to keep the application relatively small

# Technology Choices

## Kotlin & Jetpack Compose

I chose Kotlin because it is the standard language for modern Android development, 
and it was also the language I encountered most frequently while learning Android.
For the UI, I used Jetpack Compose. Since the Android Studio project template already used Compose

## SensorManager

To read the device orientation, I used Android's `SensorManager` together with the rotation vector sensor.

## Room

For local persistence, I considered three different options:

* File storage
* DataStore
* Room

A file-based solution would probably have been the quickest to implement, 
but it felt less suitable once I wanted to save multiple readings and display them again.
I ultimately chose Room because each reading naturally fits into a structured database record containing an id, azimuth, direction, and timestamp.


# Project Structure

Although the application is relatively small, I wanted to avoid putting everything inside `MainActivity`.

The project is divided into four main packages:

data
domain
sensor
ui
```

### data

Contains everything related to local persistence:

* CompassDatabase
* SensorReading
* SensorReadingDao
* SensorRepository

The repository acts as a small layer between the ViewModel and Room.

### domain

Contains application logic that isn't directly related to Android or the user interface.

For this project, the `CompassDirection` object converts an azimuth value into one of the eight compass directions

### sensor

Contains the code responsible for communicating with Android's sensor framework.

`CompassSensorReader` listens for sensor updates and forwards the current heading back to the ViewModel.

### ui

Contains the Compose screen, UI state, theme and ViewModel.

The application follows a simple MVVM-inspired structure.

The sensor flow looks roughly like this:


SensorManager
      │
      ▼
CompassSensorReader
      │
      ▼
CompassViewModel
      │
      ▼
CompassUiState
      │
      ▼
CompassScreen

Saving a reading follows this flow:

CompassScreen
      │
      ▼
CompassViewModel
      │
      ▼
SensorRepository
      │
      ▼
SensorReadingDao
      │
      ▼
Room Database

While building the project, I also tried to follow the Single Responsibility Principle where it made sense.

For example:

* `CompassSensorReader` is responsible for communicating with Android sensors.
* `CompassViewModel` manages the application's UI state.
* `CompassScreen` is only responsible for displaying data and forwarding user actions.
* `SensorRepository` handles persistence.
* `CompassDirection` contains the logic for converting degrees into compass directions.

Keeping these responsibilities separate helped prevent individual classes from becoming too large and made the project easier to understand.
# Challenges

The biggest challenge was not only writing Kotlin itself, but understanding how the Android ecosystem fits together.

A significant amount of time was spent learning:
* Android Studio and the project structure
* Gradle dependencies
* Compose state management
* ViewModels
* Room
* Android sensors

One issue of the issues I encountered was configuring Room together with the Android Studio version I was using. 
I initially ran into dependency and KSP compatibility issues, 
which required updating package versions and adjusting the Gradle configuration before everything worked correctly.
Overall, the assignment took considerably longer than I initially expected,
because much of the time was spent learning new technologies rather than simply writing code.

# Reflections

Since this was my first Android project, I deliberately kept the application relatively small.

My goal was to understand how data moves through an Android application instead of trying to build additional functionality

The biggest takeaway for me was understanding how separating the application into smaller layers made the project easier to reason about. 
Keeping the sensor handling, persistence, and UI in different classes prevented `MainActivity` from becoming responsible for everything.


# Future Improvements

If I continued developing the application, I would probably add:

* Better formatting of saved readings
* Unit tests for the compass direction calculations
* A dedicated history screen
* Delete / clear history functionality
* Cloud synchronization
* A clearer separation between local and remote data sources if online storage was introduced

# References & Learning Resources

During the project, I primarily relied on the official Android documentation while learning unfamiliar concepts and APIs, 
but for coding and using the concepts, I relied on alot of videos aswell

### Android Studio & Project Setup

* Android Studio: https://developer.android.com/studio
* Build your first Android app: https://developer.android.com/codelabs/basic-android-kotlin-compose-first-app

### Jetpack Compose

* https://developer.android.com/compose
* https://developer.android.com/develop/ui/compose/tutorial
* https://developer.android.com/develop/ui/compose/state
* https://developer.android.com/codelabs/jetpack-compose-state

### Sensors

* SensorManager: https://developer.android.com/reference/android/hardware/SensorManager
* Sensors overview: https://developer.android.com/develop/sensors-and-location/sensors/sensors_overview
* Position sensors: https://developer.android.com/develop/sensors-and-location/sensors/sensors_position
* SensorEventListener: https://developer.android.com/reference/android/hardware/SensorEventListener

### Architecture

* ViewModel: https://developer.android.com/topic/libraries/architecture/viewmodel
* ViewModel Factories: https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories
* Kotlin Coroutines on Android: https://developer.android.com/kotlin/coroutines

### Room

* Room Persistence Library: https://developer.android.com/training/data-storage/room
* Room API Reference: https://developer.android.com/reference/androidx/room/package-summary
* Room Database Builder: https://developer.android.com/reference/androidx/room/Room

### AI-assisted Learning

Throughout the project I also used ChatGPT. I mainly used it to understand Android concepts that were new to me and for error message understanding. 
Since I didnt have alot of experience with writing and running Android applications, alot of the error messages were hard to decipher. 
The GPT was, besides for learning and finding resources, used to understand the error messages in reference to my code.
