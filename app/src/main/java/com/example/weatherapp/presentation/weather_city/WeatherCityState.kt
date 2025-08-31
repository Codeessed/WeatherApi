package com.example.weatherapp.presentation.weather_city

import com.example.weatherapp.domain.model.WeatherGeo

data class WeatherCityState(
    val data: List<WeatherGeo> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val message: String = "",
)
