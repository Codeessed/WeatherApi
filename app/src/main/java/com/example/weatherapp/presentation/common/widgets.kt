package com.example.weatherapp.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.presentation.weather_city.WeatherCityEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    isFirstPage: Boolean = true,
    text: String,
    title: String,
    onTextChange: (String) -> Unit,
    onNavigationClick: () -> Unit
){
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.7f)
        ),
        title = {
            if(isFirstPage)
            OutlinedTextField(
                value = text,
                onValueChange = onTextChange,
                modifier = modifier
                    .fillMaxWidth(1f),
                placeholder = {
                    Text(text = "Input city name")
                },
                suffix = {
                    Icon(
                        modifier = Modifier
                            .size(25.dp),
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null,
                    )
                },
                maxLines = 1,
                singleLine = true,
                shape = RoundedCornerShape(0.dp), // remove rounded corners
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent)
            )

            if(!isFirstPage)
                Text(text = "")
        },
        navigationIcon = {
            if(!isFirstPage)
                IconButton(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 10.dp)
                        .size(25.dp),
                    onClick = onNavigationClick
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null,
                    )
                }
        },

    )

}