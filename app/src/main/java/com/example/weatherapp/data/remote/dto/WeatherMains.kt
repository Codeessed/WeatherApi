package com.example.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherMains(
    val temp: Any,
    val pressure: Any,
    val humidity: Any,
    @SerializedName("sea_level")
    val seaLevel: Any
)
