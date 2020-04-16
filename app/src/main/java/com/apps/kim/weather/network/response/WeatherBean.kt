package com.apps.kim.weather.network.response

import com.apps.kim.weather.tools.base.converter.BaseConverter
import com.apps.kim.weather.tools.base.converter.Converter

/**
Created by KIM on 16.04.2020
 **/

data class WeatherBean(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<WeatherData>?,
    val message: Int?
)

data class City(
    val coord: Coord?,
    val country: String?,
    val id: Int?,
    val name: String?,
    val sunrise: Int?,
    val sunset: Int?,
    val timezone: Int?
)

data class WeatherData(
    val clouds: Clouds?,
    val dt: Int?,
    val dt_txt: String?,
    val main: Main?,
    val rain: Rain?,
    val sys: Sys?,
    val weather: List<Weather>?,
    val wind: Wind?
)

data class Coord(
    val lat: Double?,
    val lon: Double?
)

data class Clouds(
    val all: Int?
)

data class Main(
    val feels_like: Double?,
    val grnd_level: Int?,
    val humidity: Int?,
    val pressure: Int?,
    val sea_level: Int?,
    val temp: Double?,
    val temp_kf: Double?,
    val temp_max: Double?,
    val temp_min: Double?
)

data class Rain(
    val `3h`: Double?
)

data class Sys(
    val pod: String?
)

data class Weather(
    val description: String?,
    val icon: String?,
    val id: Int?,
    val main: String?
)

data class Wind(
    val deg: Int?,
    val speed: Double?
)

data class BeanModel(
    val city: String?,
    val list: List<DataModel>?
)

data class DataModel(
    val dt: Int?,
    val dtTxt: String?,
    val windSpeed: Double?,
    val temp: Double?,
    val tempMax: Double?,
    val tempMin: Double?,
    val weather: List<WeatherModel>?
)

data class WeatherModel(
    val description: String?,
    val icon: String?,
    val id: Int?,
    val main: String?
)

interface IWeatherConverter : Converter<Weather, WeatherModel>
class WeatherConverter : BaseConverter<Weather, WeatherModel>(), IWeatherConverter {
    override fun processConvertInToOut(inObject: Weather): WeatherModel = inObject.run {
        WeatherModel(description, icon, id, main)
    }

    override fun processConvertOutToIn(outObject: WeatherModel): Weather {
        TODO("Not yet implemented")
    }
}

interface IDataConverter : Converter<WeatherData, DataModel>
class DataConverter : BaseConverter<WeatherData, DataModel>(), IDataConverter {
    override fun processConvertInToOut(inObject: WeatherData): DataModel = inObject.run {
        DataModel(
            dt,
            dt_txt,
            this.wind?.speed,
            this.main?.temp,
            this.main?.temp_max,
            this.main?.temp_min,
            weather?.let { WeatherConverter().convertListInToOut(it) }
        )
    }

    override fun processConvertOutToIn(outObject: DataModel): WeatherData {
        TODO("Not yet implemented")
    }
}

interface IBeanConverter : Converter<WeatherBean, BeanModel>
class BeanConverter : BaseConverter<WeatherBean, BeanModel>(), IBeanConverter {
    override fun processConvertInToOut(inObject: WeatherBean): BeanModel = inObject.run {
        BeanModel(this.city?.name, list?.let { DataConverter().convertListInToOut(it) })
    }

    override fun processConvertOutToIn(outObject: BeanModel): WeatherBean {
        TODO("Not yet implemented")
    }
}
