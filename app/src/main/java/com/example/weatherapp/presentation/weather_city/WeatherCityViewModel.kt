package com.example.weatherapp.presentation.weather_city

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.mapper.toWeatherGeoMap
import com.example.weatherapp.data.mapper.updateSelectedGeo
import com.example.weatherapp.domain.model.WeatherGeo
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherCityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
): ViewModel() {

    var cityScreenState  by  mutableStateOf(WeatherCityState())

    var cityStateList  by  mutableStateOf<Map<String, Boolean>>(hashMapOf())

    val savedCity: StateFlow<String?> = weatherRepository.getCityName().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null
    )

    var selectedWeatherGeo  by  mutableStateOf(
        WeatherGeo(
            name = "",
            latitude = "",
            longitude = "",
            country = "",
            state = ""
        )
    )

    init {
        viewModelScope.launch {
            savedCity.collect(){
                onEvent(
                    WeatherCityEvent.OnSearchQueryChange(
                        it ?: "saved"
                    )
                )
            }
        }
    }

    private var searchCityJob: Job? = null

    fun onEvent(event: WeatherCityEvent){
        when(event){
            is WeatherCityEvent.OnSearchQueryChange -> {
                val query = event.query
                searchCityJob?.cancel()
                cityScreenState = cityScreenState.copy(
                    searchQuery = query,
                )
                if(query.isEmpty()){
                    cityScreenState = cityScreenState.copy(
                        data = emptyList()
                    )
                    cityStateList = hashMapOf()
                }else{
                    searchCityJob = viewModelScope.launch {
                        delay(1000L)
                        cityScreenState = cityScreenState.copy(
                            isLoading = true
                        )
                        val weatherGeoResult = weatherRepository.getWeatherGeo(query)
                        cityScreenState = cityScreenState.copy(
                            isLoading = false,
                            data = weatherGeoResult.data ?: emptyList(),
                            message = weatherGeoResult.message ?: ""
                        )
                        cityStateList = cityScreenState.data.toWeatherGeoMap() ?: hashMapOf()
                    }
                }

            }
            is WeatherCityEvent.OnCityItemClick -> {

                selectedWeatherGeo = cityScreenState.data.find { geoDetails ->
                    geoDetails.latitude == event.lat && geoDetails.longitude == event.long
                }!!

                cityStateList = cityStateList.updateSelectedGeo(
                    selectedKey = selectedWeatherGeo.country,
                )

            }
        }
    }


}