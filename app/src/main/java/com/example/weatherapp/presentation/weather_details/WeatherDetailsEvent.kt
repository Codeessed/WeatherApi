package com.example.weatherapp.presentation.weather_details

sealed class WeatherDetailsEvent {
    data class OnGetCityDetails(val lat: String, val lon: String) : WeatherDetailsEvent()
    data class OnCitySaved(val query: String) : WeatherDetailsEvent()
    object OnNavigateBack : WeatherDetailsEvent()
}