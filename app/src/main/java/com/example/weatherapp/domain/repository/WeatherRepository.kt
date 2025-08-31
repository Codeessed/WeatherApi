package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.WeatherGeo
import com.example.weatherapp.util.Resource

interface WeatherRepository {

    suspend fun getWeatherGeo(
        query: String
    ): Resource<List<WeatherGeo>>


}