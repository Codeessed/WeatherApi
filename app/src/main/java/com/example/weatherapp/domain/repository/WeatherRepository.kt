package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.WeatherDetails
import com.example.weatherapp.domain.model.WeatherGeo
import com.example.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeatherGeo(
        query: String
    ): Resource<List<WeatherGeo>>

    suspend fun getWeatherDetails(
        lat: String,
        lon: String,
    ): Resource<WeatherDetails>

    suspend fun saveCityName(
        city: String,
    )

    fun getCityName():Flow<String>




}