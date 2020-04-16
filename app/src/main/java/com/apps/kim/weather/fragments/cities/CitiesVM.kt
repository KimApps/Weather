package com.apps.kim.weather.fragments.cities

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.apps.kim.weather.app.API_KEY
import com.apps.kim.weather.app.UNITS
import com.apps.kim.weather.network.api.Api
import com.apps.kim.weather.network.response.BeanModel
import com.apps.kim.weather.tools.base.BaseVM
import com.apps.kim.weather.tools.utils.LOG

/**
Created by KIM on 16.04.2020
 **/

class CitiesVM(application: Application) : BaseVM(application) {
    private val api = Api.instance.api
    val listLD = MutableLiveData<MutableList<BeanModel>>()
    val bean = MutableLiveData<BeanModel>()

    fun getWeather(city: String) {
        LOG.v(message = "${this.javaClass.simpleName}|${object {}.javaClass.enclosingMethod?.name}| $city")
        api.getWeatherCity(
            city,
            API_KEY,
            UNITS
        ).doAsync(bean, onErrorConsumer)
    }
}