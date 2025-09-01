package com.example.weatherapp.domain.model

import com.example.weatherapp.data.remote.dto.WeatherItem

data class WeatherDetails(
    val name: String,
    val timezone: String,
    val windSpeed: String,
    val weatherTemp: String,
    val weatherPressure: String,
    val weatherHumidity: String,
    val weatherSeaLevel: String,
    val weathers: List<WeatherItem>
)
