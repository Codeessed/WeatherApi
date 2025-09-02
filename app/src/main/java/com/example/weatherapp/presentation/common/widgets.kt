package com.example.weatherapp.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: @Composable ()-> Unit,
    navigationIcon: @Composable ()-> Unit
){
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.7f)
        ),
        title = title,
        navigationIcon = navigationIcon
    )

}


@Composable
fun TopBarNavigationIcon(
    onTopBarNavigationIconClick: () -> Unit,
    icon: ImageVector
){
    IconButton(
        modifier = Modifier
            .padding(start = 16.dp, end = 10.dp)
            .size(25.dp),
        onClick = onTopBarNavigationIconClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
        )
    }
}

@Composable
fun SearchCityTextField(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    text: String
){
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange ,
        modifier = modifier
            .fillMaxWidth(1f),
        placeholder = {
            Text(text = "Input city name")
        },
        suffix = {
            Icon(
                modifier = Modifier.padding(start = 10.dp),
                imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
            )
        },
        prefix = {
            Icon(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(25.dp),
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
            )
        },
        maxLines = 1,
        singleLine = true,
        shape = RoundedCornerShape(0.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent
        )
    )
}