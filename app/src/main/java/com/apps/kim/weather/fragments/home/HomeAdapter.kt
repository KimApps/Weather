package com.apps.kim.weather.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apps.kim.weather.R
import com.apps.kim.weather.app.EMPTY_STRING
import com.apps.kim.weather.network.response.DataModel
import com.apps.kim.weather.tools.extensions.loadImage
import com.apps.kim.weather.tools.utils.AppUtil
import com.apps.kim.weather.tools.utils.LOG

/**
Created by KIM on 16.04.2020
 **/
class HomeAdapter(private var list: List<DataModel>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        )

    override fun onBindViewHolder(h: ViewHolder, position: Int) {
        val item = list[position]
        h.apply {
            date.text = item.dtTxt?.let { AppUtil.getDate(it) }
            val temperature = item.temp?.let { AppUtil.getTemp(it.toString()) }
            temp.text = temperature?.let { "$it°C" }
            val url = AppUtil.getIcon(item.weather?.get(0)?.icon ?: EMPTY_STRING)
            LOG.v(message = "${this.javaClass.simpleName}|${object {}.javaClass.enclosingMethod?.name}| $url")
            icon.loadImage(url)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date = itemView.findViewById(R.id.txtItemHomeDate) as TextView
        val temp = itemView.findViewById(R.id.txtItemHomeTemp) as TextView
        val icon = itemView.findViewById(R.id.imgItemHome) as ImageView
    }
}