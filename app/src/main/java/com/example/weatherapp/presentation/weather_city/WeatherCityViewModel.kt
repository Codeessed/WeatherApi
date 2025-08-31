package com.example.weatherapp.presentation.weather_city

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.mapper.toWeatherGeoMap
import com.example.weatherapp.data.mapper.updateSelectedGeo
import com.example.weatherapp.domain.model.WeatherGeo
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherCityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    var cityScreenState  by  mutableStateOf(WeatherCityState())

    var cityStateList  by  mutableStateOf<Map<String, Boolean>>(hashMapOf())

    private val _cityGeoCode = Channel<String>()
    val cityGeoCode = _cityGeoCode.receiveAsFlow()

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


//                    _cityGeoCode.send(geoData)
                    }
                }

            }
            is WeatherCityEvent.OnGetWeatherDetail -> {


            }
            is WeatherCityEvent.OnCityItemClick -> {

                val selectedGeo = cityScreenState.data.find { geoDetails ->
                    geoDetails.latitude == event.lat && geoDetails.longitude == event.long
                }

                cityStateList = cityStateList.updateSelectedGeo(
                    selectedKey = selectedGeo!!.country,
                )

            }
        }
    }


}