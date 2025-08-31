package com.example.weatherapp.data.mapper

import android.util.Log
import com.example.weatherapp.data.remote.dto.WeatherGeoDto
import com.example.weatherapp.domain.model.WeatherGeo


fun WeatherGeoDto.toWeatherGeoList(): List<WeatherGeo>{
    return this.map { dto ->
        WeatherGeo(
            name = dto.name,
            country = dto.country,
            latitude = dto.latitude.toString(),
            longitude = dto.longitude.toString(),
            state = dto.state ?: ""
        )
    }
}

fun List<WeatherGeo>.toWeatherGeoMap(): HashMap<String, Boolean> {
    val result =  this.associate { dto->
        dto.country to false
    } as HashMap<String, Boolean>
    return result
}

fun Map<String, Boolean>.updateSelectedGeo(selectedKey: String): HashMap<String, Boolean> {
    val result =  this.entries.associate { dto->
        if(selectedKey == dto.key){
            dto.key to !dto.value
        }else{
            dto.key to false
        }
    } as HashMap<String, Boolean>
    return result
}
