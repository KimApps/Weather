package com.apps.kim.weather.fragments.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.apps.kim.weather.app.API_KEY
import com.apps.kim.weather.app.DOUBLE_ZERO
import com.apps.kim.weather.app.UNITS
import com.apps.kim.weather.network.api.Api
import com.apps.kim.weather.network.response.BeanModel
import com.apps.kim.weather.tools.base.BaseVM
import com.apps.kim.weather.tools.classes.CityModel
import com.apps.kim.weather.tools.utils.PrefProvider

/**
Created by KIM on 15.04.2020
 **/

class HomeVM(application: Application) : BaseVM(application) {
    private val api = Api.instance.api
    var latLD = MutableLiveData<String>()
    var longLD = MutableLiveData<String>()
    val bean = MutableLiveData<BeanModel>()
    fun getWeather() {
        api.getWeatherLocation(
            PrefProvider.latitude.toDoubleOrNull() ?: DOUBLE_ZERO,
            PrefProvider.longitude.toDoubleOrNull() ?: DOUBLE_ZERO,
            API_KEY,
            UNITS
        ).doAsync(bean, onErrorConsumer, false)
    }

}