package com.example.weatherapp.data.mapper

import android.util.Log
import com.example.weatherapp.data.remote.dto.WeatherDetailsDto
import com.example.weatherapp.domain.model.WeatherDetails

fun WeatherDetailsDto.toWeatherDetails(): WeatherDetails{

    return WeatherDetails(
        name = this.name.toString(),
        timezone = this.timezone.toString(),
        windSpeed = this.wind.speed.toString(),
        weatherTemp = this.main.temp.toString(),
        weatherPressure = this.main.pressure.toString(),
        weatherHumidity = this.main.humidity.toString(),
        weatherSeaLevel = this.main.seaLevel.toString(),
        weathers = this.weather
    )

}