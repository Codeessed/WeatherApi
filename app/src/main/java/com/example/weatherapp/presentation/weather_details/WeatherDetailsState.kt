package com.example.weatherapp.presentation.weather_details

import com.example.weatherapp.domain.model.WeatherDetails
import com.example.weatherapp.domain.model.WeatherGeo

data class WeatherDetailsState(
    val data: WeatherDetails? = null,
    val isLoading: Boolean = false,
    val message: String = "",
)
