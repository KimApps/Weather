package com.apps.kim.weather.fragments.cities

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.kim.weather.R
import com.apps.kim.weather.network.response.BeanModel
import com.apps.kim.weather.tools.base.BaseLifecycleFragment
import com.apps.kim.weather.tools.base.BundleDelegate
import com.apps.kim.weather.tools.extensions.setClickListeners
import com.apps.kim.weather.tools.utils.LOG
import com.apps.kim.weather.tools.utils.bindInterfaceOrThrow
import kotlinx.android.synthetic.main.fr_cities.*

/**
Created by KIM on 16.04.2020
 **/

class CitiesFR : BaseLifecycleFragment<CitiesVM>() {

    companion object {
        fun newInstance(cities: String) = CitiesFR().apply { this.cities = cities }
    }

    override val layoutId = R.layout.fr_cities
    override val viewModelClass = CitiesVM::class.java
    private var callback: CitiesCB? = null
    private var cities by BundleDelegate<String>()

    private val snackTextObserver = Observer<String> { showSnackbar(it) }
    private val beanObserver = Observer<BeanModel> {
        LOG.v(message = "${this.javaClass.simpleName}|${object {}.javaClass.enclosingMethod?.name}| ${it.city}")
        val list = vm.listLD.value ?: mutableListOf()
        list.add(it)
        vm.listLD.value = list
    }
    private val listObserver = Observer<List<BeanModel>> {
        LOG.v(message = "${this.javaClass.simpleName}|${object {}.javaClass.enclosingMethod?.name}| ${it.size}")
        initRecycler(it)
    }

    override fun observeLiveData() {
        vm.run {
            snackTextLD.observe(this@CitiesFR, snackTextObserver)
            listLD.observe(this@CitiesFR, listObserver)
            bean.observe(this@CitiesFR, beanObserver)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        LOG.v(message = "${this.javaClass.simpleName}|${object {}.javaClass.enclosingMethod?.name}| $cities")
        cities?.let {
            val list = it.split(",")
            for (item in list) vm.getWeather(item.trim())
        }
    }

    private fun initRecycler(list: List<BeanModel>) {
        LOG.v(message = "${this.javaClass.simpleName}|${object {}.javaClass.enclosingMethod?.name}| list size: ${list.size}")
        rvCities.apply {
            adapter = CitiesAdapter(list)
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = bindInterfaceOrThrow<CitiesCB>(parentFragment, context)
    }

    override fun onDetach() {
        callback = null
        super.onDetach()
    }
}