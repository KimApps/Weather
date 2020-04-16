package com.apps.kim.weather.tools.utils

import android.view.View
import com.apps.kim.weather.R
import com.apps.kim.weather.app.App
import com.apps.kim.weather.app.EMPTY_STRING
import com.apps.kim.weather.app.STRING_ZERO
import com.apps.kim.weather.tools.classes.CityModel
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.NumberFormat

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
object AppUtil {

    fun getFormat(): NumberFormat {
        val nf = NumberFormat.getInstance()
        nf.apply {
            maximumFractionDigits = 2
            minimumFractionDigits = 2
        }
        return nf
    }

    fun phoneFormat(number: String): String {
        return if (number.isNotEmpty()) {
            if (number[0].toString() == STRING_ZERO) number.substring(1, number.length)
            else number
        } else number
    }

    fun getCities(str: String): MutableList<CityModel> {
        val list = Gson().fromJson(
            BufferedReader(
                InputStreamReader(
                    App.instance.applicationContext.resources.openRawResource(
                        R.raw.cities
                    )
                )
            ),
            Array<CityModel>::class.java
        ).toList()
        val result = mutableListOf<CityModel>()
        for (city in list) if (city.name.contains(str, true)) result.add(city)
        return result
    }

    fun getIcon(icon: String): String = "https://openweathermap.org/img/w/$icon.png"
    fun getDate(text: String): String = text.substring(5, text.length - 3)
    fun getTemp(temp: String): String {
        val list = temp.split(".")
        return if (list.isNotEmpty()) list[0]
        else EMPTY_STRING
    }

    fun hideSlide(v: View?) = v?.animate()?.translationY((v.height).toFloat())?.alpha(1.0f)
    fun showSlide(v: View?) = v?.animate()?.translationY(0f)
}