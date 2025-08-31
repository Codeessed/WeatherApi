package com.example.weatherapp.data.repository

import com.example.weatherapp.data.mapper.toWeatherGeoList
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.model.WeatherGeo
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherRepository{

    override suspend fun getWeatherGeo(query: String): Resource<List<WeatherGeo>> {
        return try {
            val response = weatherApi.getCityGeo(query = query)
            Resource.Success(
                response.toWeatherGeoList()
            )
        }catch (e: Exception){
            Resource.Error("An error occurred: $e")
        }
    }


}