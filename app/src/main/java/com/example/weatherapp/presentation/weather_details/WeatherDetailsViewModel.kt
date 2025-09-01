package com.example.weatherapp.presentation.weather_details

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
class WeatherDetailsViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    var detailsScreenState  by  mutableStateOf(WeatherDetailsState())


    private var _weatherDetailsChannel = Channel<WeatherDetailsState>()
    val weatherDetailsChannel = _weatherDetailsChannel.receiveAsFlow()

    fun onEvent(event: WeatherDetailsEvent){
        when(event){
            is WeatherDetailsEvent.OnCitySaved -> {
                val query = event.query
            }
            is WeatherDetailsEvent.OnNavigateBack -> {

            }
            is WeatherDetailsEvent.OnGetCityDetails -> {
                viewModelScope.launch {
                    detailsScreenState = detailsScreenState.copy(
                        isLoading = true
                    )
                    _weatherDetailsChannel.send(
                        detailsScreenState.copy(
                            isLoading = true
                        )
                    )

                    val detailsResult = weatherRepository.getWeatherDetails(lat = event.lat, lon = event.lon)
                    detailsScreenState = detailsScreenState.copy(
                        isLoading = false,
                        data = detailsResult.data,
                        message = detailsResult.message ?: ""
                    )
                    _weatherDetailsChannel.send(
                        detailsScreenState.copy(
                            isLoading = false,
                            data = detailsResult.data,
                            message = detailsResult.message ?: ""
                        )
                    )

                }

            }
            else -> Unit
        }
    }


}