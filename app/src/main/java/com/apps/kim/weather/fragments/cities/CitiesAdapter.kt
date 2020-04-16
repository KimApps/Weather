package com.apps.kim.weather.fragments.cities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apps.kim.weather.R
import com.apps.kim.weather.app.App
import com.apps.kim.weather.app.EMPTY_STRING
import com.apps.kim.weather.fragments.home.HomeAdapter
import com.apps.kim.weather.network.response.BeanModel
import com.apps.kim.weather.tools.extensions.loadImage
import com.apps.kim.weather.tools.utils.AppUtil

/**
Created by KIM on 16.04.2020
 **/

class CitiesAdapter(private var list: List<BeanModel>) :
    RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cities, parent, false)
        )

    override fun onBindViewHolder(h: ViewHolder, position: Int) {
        val item = list[position]
        h.apply {
            val url = AppUtil.getIcon(item.list?.get(0)?.weather?.get(0)?.icon ?: EMPTY_STRING)
            icon.loadImage(url)
            city.text = item.city
            temp.text = "${AppUtil.getTemp(item.list?.get(0)?.temp.toString())}°C"
            min.text = "${AppUtil.getTemp(item.list?.get(0)?.tempMin.toString())}°C"
            max.text = "${AppUtil.getTemp(item.list?.get(0)?.tempMax.toString())}°C"
            speed.text = "${item.list?.get(0)?.windSpeed.toString()} \nm/sec"
            recycler.apply {
                adapter = HomeAdapter(item.list ?: listOf())
                layoutManager = LinearLayoutManager(
                    App.instance.applicationContext,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                setHasFixedSize(true)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val city = itemView.findViewById(R.id.txtItemCitiesCity) as TextView
        val icon = itemView.findViewById(R.id.imgItemCitiesIcon) as ImageView
        val temp = itemView.findViewById(R.id.txtItemCitiesTemp) as TextView
        val min = itemView.findViewById(R.id.txtItemCitiesMin) as TextView
        val max = itemView.findViewById(R.id.txtItemCitiesMax) as TextView
        val speed = itemView.findViewById(R.id.txtItemCitiesWind) as TextView
        val recycler = itemView.findViewById(R.id.rvItemCities) as RecyclerView
    }
}