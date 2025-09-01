package com.example.weatherapp.presentation.weather_city

sealed class WeatherCityEvent {
    data class OnCityItemClick(val lat: String, val long: String) : WeatherCityEvent()
    data class OnSearchQueryChange(val query: String): WeatherCityEvent()
}