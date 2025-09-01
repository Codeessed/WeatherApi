package com.example.weatherapp.presentation.weather_city

import android.content.ClipData.Item
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.navigation.MainNavGraph
import com.example.weatherapp.presentation.destinations.WeatherDetailsScreenDestination
//import com.example.weatherapp.presentation.destinations.WeatherDetailsScreenDestination
import com.example.weatherapp.presentation.weather_details.WeatherDetailsEvent
import com.example.weatherapp.presentation.weather_details.WeatherDetailsViewModel
import com.example.weatherapp.util.ObserveAsEvents
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainNavGraph(start = true)
@Destination
@Composable
fun WeatherCityScreen(
    navigator: DestinationsNavigator,
    viewModel: WeatherCityViewModel = hiltViewModel(),
    detailsViewModel: WeatherDetailsViewModel,
){

    val screenState = viewModel.cityScreenState
    val detailsChannelState = detailsViewModel.weatherDetailsChannel
    val cityStatePickedMap = viewModel.cityStateList
    val scrollState = rememberScrollState()
    val detailsLoading = remember { mutableStateOf(false) }


    ObserveAsEvents(flow = detailsChannelState){ detailsChannel ->
        detailsLoading.value = detailsChannel.isLoading
        if(detailsChannel.data != null)
            navigator.navigate(
                direction =  WeatherDetailsScreenDestination
            )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = screenState.searchQuery,
                onValueChange = {
                    viewModel.onEvent(
                        WeatherCityEvent.OnSearchQueryChange(it)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                placeholder = {
                    Text(text = "City name")
                },
                maxLines = 1,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(50.dp))
            if(cityStatePickedMap.isNotEmpty()){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)

                ) {
                    repeat(screenState.data.size){index ->
                        val weatherGeo = screenState.data.elementAt(index)
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp)
                                .clickable {
                                    viewModel.onEvent(
                                        WeatherCityEvent.OnCityItemClick(
                                            lat = weatherGeo.latitude,
                                            long = weatherGeo.longitude
                                        )
                                    )
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = cityStatePickedMap.values.elementAt(index),
                                onClick = {
                                    viewModel.onEvent(
                                        WeatherCityEvent.OnCityItemClick(
                                            lat = weatherGeo.latitude,
                                            long = weatherGeo.longitude
                                        )
                                    )
                                }
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 10.dp),
                                text = "${weatherGeo.name}, ${weatherGeo.state}. ${weatherGeo.country}",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    Button(
                        onClick = {
                            detailsViewModel.onEvent(
                                WeatherDetailsEvent.OnGetCityDetails(
                                    lat = viewModel.selectedWeatherGeo.latitude,
                                    lon = viewModel.selectedWeatherGeo.longitude
                                )
                            )
                        },
                        enabled = cityStatePickedMap.values.contains(true) ,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    ) {
                        Text(text = "Continue")
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                }
            }


            if(cityStatePickedMap.isEmpty() && !screenState.isLoading)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {
                    Spacer(modifier = Modifier.height(100.dp))
                    if(screenState.message.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = screenState.message,
                            textAlign = TextAlign.Center,
                            color = Color.Red
                        )
                    }else{
                        if(screenState.searchQuery.isEmpty()){
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Enter city name to continue",
                                textAlign = TextAlign.Center,
                            )
                        }else{
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Specified city name is not available",
                                textAlign = TextAlign.Center,
                                color = Color.Red
                            )
                        }
                    }

                }

        }
        if(screenState.isLoading || detailsLoading.value)
            ProgressBox()
    }



}

@Composable
fun ProgressBox(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center,
    ){
        CircularProgressIndicator(progress = 1f)
    }
}