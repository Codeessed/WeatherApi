package com.example.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherGeoItemDto(
    val name: String,
    @SerializedName("lat")
    val latitude: Any,
    @SerializedName("lon")
    val longitude: Any,
    val country: String,
    val state: String?,
): Serializable
