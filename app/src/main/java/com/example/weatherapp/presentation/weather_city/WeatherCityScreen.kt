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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun WeatherCityScreen(
    navigator: DestinationsNavigator,
    viewModel: WeatherCityViewModel = hiltViewModel()
){

    val screenState = viewModel.cityScreenState
    val cityStatePickedMap = viewModel.cityStateList
    val scrollState = rememberScrollState()

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

            if(cityStatePickedMap.isNotEmpty())
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
                    onClick = {  },
                    enabled = cityStatePickedMap.values.contains(true) ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                ) {
                    Text(text = "Continue")
                }
                Spacer(modifier = Modifier.height(20.dp))

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
        if(screenState.isLoading)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)),
            contentAlignment = Alignment.Center,

        ){
            CircularProgressIndicator(progress = 1f)
        }
    }



}