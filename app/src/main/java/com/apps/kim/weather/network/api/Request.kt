package com.apps.kim.weather.network.api

import com.apps.kim.weather.network.requests.LoginRQ
import com.apps.kim.weather.network.response.LoginBean
import com.apps.kim.weather.network.response.WeatherBean
import io.reactivex.Single
import retrofit2.http.*

/**
Created by KIM on 04.11.2019
 **/

interface Request {
    /** AUTHORIZATION REQUESTS**/
    @POST("apilogin")
    fun login(@Body request: LoginRQ): Single<LoginBean>

    @GET("forecast")
    fun getWeatherCity(
        @Query("q") city: String,
        @Query("appid") appid: String,
        @Query("units") units: String
    ): Single<WeatherBean>

    @GET("forecast")
    fun getWeatherLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("units") units: String
    ): Single<WeatherBean>

}