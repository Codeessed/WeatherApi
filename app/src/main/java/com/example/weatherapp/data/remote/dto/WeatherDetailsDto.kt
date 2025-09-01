package com.example.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherDetailsDto(
    val weather: List<WeatherItem>,
    val main: WeatherMains,
    val wind: WeatherWind,
    val timezone: Any,
    val name: Any
)
