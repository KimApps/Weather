package com.apps.kim.weather.network.api

import com.apps.kim.weather.network.requests.LoginRQ
import com.apps.kim.weather.network.response.BeanConverter
import com.apps.kim.weather.network.response.BeanModel
import com.apps.kim.weather.network.response.LoginBean
import com.apps.kim.weather.tools.utils.NetworkErrorUtils
import io.reactivex.Single

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
class ApplicationApi(private val mRequest: Request) {

    /** AUTHORIZATION REQUESTS **/
    fun login(request: LoginRQ): Single<LoginBean> = mRequest.login(request)
        .onErrorResumeNext(NetworkErrorUtils.rxParseError())

    fun getWeatherCity(city: String, appid: String, units: String): Single<BeanModel> =
        mRequest.getWeatherCity(city, appid, units)
            .map { BeanConverter().convertInToOut(it) }
            .onErrorResumeNext(NetworkErrorUtils.rxParseError())

    fun getWeatherLocation(
        lat: Double,
        lon: Double,
        appid: String,
        units: String
    ): Single<BeanModel> =
        mRequest.getWeatherLocation(lat, lon, appid, units)
            .map { BeanConverter().convertInToOut(it) }
            .onErrorResumeNext(NetworkErrorUtils.rxParseError())


}