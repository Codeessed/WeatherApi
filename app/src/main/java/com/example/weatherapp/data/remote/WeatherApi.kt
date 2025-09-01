package com.example.weatherapp.data.remote

import android.os.Debug
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.remote.dto.WeatherDetailsDto
import com.example.weatherapp.data.remote.dto.WeatherGeoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("geo/1.0/direct")
    suspend fun getCityGeo(
        @Query("appid") apiKey: String = BuildConfig.WEATHER_API_KEY,
        @Query("q") query: String
    ): WeatherGeoDto

    @GET("data/2.5/weather")
    suspend fun getWeatherData(
        @Query("appid") apiKey: String = BuildConfig.WEATHER_API_KEY,
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): WeatherDetailsDto



}