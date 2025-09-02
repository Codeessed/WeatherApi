package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.presentation.NavGraphs
import com.example.weatherapp.presentation.common.SearchCityTextField
import com.example.weatherapp.presentation.common.TopBar
import com.example.weatherapp.presentation.common.TopBarNavigationIcon
import com.example.weatherapp.presentation.weather_city.WeatherCityEvent
import com.example.weatherapp.presentation.weather_city.WeatherCityViewModel
import com.example.weatherapp.presentation.weather_details.WeatherDetailsViewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherCityViewModel: WeatherCityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                weatherCityViewModel.savedCity.value != null
            }
        }
        enableEdgeToEdge()


        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val isRootDestination = currentDestination?.route == NavGraphs.main.startRoute.route
            WeatherAppTheme {
                Scaffold(
                    topBar = {
                        TopBar(
                            modifier = Modifier,
                            title = {
                                if(isRootDestination){
                                    SearchCityTextField(
                                        onTextChange = {
                                            weatherCityViewModel.onEvent(
                                                WeatherCityEvent.OnSearchQueryChange(
                                                    it
                                                )
                                            )
                                        },
                                        text = weatherCityViewModel.cityScreenState.searchQuery
                                    )
                                }else{
                                    Text(
                                        text = (currentDestination?.route ?: "").apply {
                                            split("_")
                                        }.toString(),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            },
                            navigationIcon = {
                                if(!isRootDestination){
                                    TopBarNavigationIcon(
                                        onTopBarNavigationIconClick = {
                                            navController.popBackStack()
                                        },
                                        icon = Icons.Rounded.ArrowBack
                                    )
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        DestinationsNavHost(
                            navGraph = NavGraphs.main,
                            navController = navController,
                            dependenciesContainerBuilder = {
                                dependency(hiltViewModel<WeatherDetailsViewModel>(this@MainActivity))
                                dependency(hiltViewModel<WeatherCityViewModel>(this@MainActivity))
                            }
                        )
                    }
                }

            }
        }
    }
}
