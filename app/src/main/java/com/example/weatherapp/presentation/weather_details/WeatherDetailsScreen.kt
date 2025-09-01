package com.example.weatherapp.presentation.weather_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.navigation.MainNavGraph
//import com.example.weatherapp.navigation.MainNavGraph
import com.example.weatherapp.presentation.weather_city.WeatherCityEvent
import com.example.weatherapp.presentation.weather_city.WeatherCityViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainNavGraph
@Composable
@Destination
fun WeatherDetailsScreen(
    viewModel: WeatherDetailsViewModel,
    cityViewModel: WeatherCityViewModel
){

    val screenState = viewModel.detailsScreenState
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        screenState.data?.let { weather ->
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Name :",
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                fontStyle = FontStyle.Italic
            )
            Text(
                text = weather.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Timezone :",
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                fontStyle = FontStyle.Italic
            )
            Text(
                text = weather.timezone,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Weathers:",
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(20.dp))
            repeat(weather.weathers.size){index ->
                val weathers = screenState.data.weathers.elementAt(index)
                Text(
                    text = "${weathers.main}: ${weathers.description}",
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                ),
                enabled = !(cityViewModel.savedCity.value ?: "").equals(cityViewModel.cityScreenState.searchQuery, ignoreCase = true),
                onClick = { viewModel.onEvent(
                    WeatherDetailsEvent.OnCitySaved(
                        cityViewModel.cityScreenState.searchQuery
                    )
                ) },
            ) {
                Text(text = "Save city")
            }
        }
    }



}