package com.example.weatherapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.weatherapp.data.mapper.toWeatherDetails
import com.example.weatherapp.data.mapper.toWeatherGeoList
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.model.WeatherDetails
import com.example.weatherapp.domain.model.WeatherGeo
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val dataStore: DataStore<Preferences>
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

    override suspend fun getWeatherDetails(lat: String, lon: String): Resource<WeatherDetails> {
        return try {
            val response = weatherApi.getWeatherData(lat = lat, lon = lon)
            Resource.Success(
                response.toWeatherDetails()
            )
        }catch (e: Exception){
            Resource.Error("An error occurred: $e")
        }
    }

    override suspend fun saveCityName(city: String) {
        dataStore.edit { prefs ->
            prefs[SAVED_CITY] = city
        }
    }

    override fun getCityName(): Flow<String> =
        dataStore.data.map { prefs ->
            prefs[SAVED_CITY] ?: ""
        }




    companion object {
        private val SAVED_CITY = stringPreferencesKey("saved_city")
    }

}