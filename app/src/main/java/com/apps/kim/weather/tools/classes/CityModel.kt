package com.apps.kim.weather.tools.classes

/**
Created by KIM on 15.04.2020
 **/

data class CityModel(
    val coord: Loc,
    val country: String,
    val id: Int,
    val name: String,
    val state: String
)

data class Loc(
    val lat: Double,
    val lon: Double
)