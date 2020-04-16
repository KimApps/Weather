package com.apps.kim.weather.fragments.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.kim.weather.R
import com.apps.kim.weather.app.App
import com.apps.kim.weather.app.DOUBLE_ZERO
import com.apps.kim.weather.app.EMPTY_STRING
import com.apps.kim.weather.network.response.BeanModel
import com.apps.kim.weather.tools.base.BaseLifecycleFragment
import com.apps.kim.weather.tools.classes.CityModel
import com.apps.kim.weather.tools.extensions.*
import com.apps.kim.weather.tools.utils.AppUtil
import com.apps.kim.weather.tools.utils.LOG
import com.apps.kim.weather.tools.utils.PrefProvider
import com.apps.kim.weather.tools.utils.bindInterfaceOrThrow
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import kotlinx.android.synthetic.main.fr_home.*

/**
Created by KIM on 15.04.2020
 **/

class HomeFR : BaseLifecycleFragment<HomeVM>(), View.OnClickListener {

    companion object {
        fun newInstance() = HomeFR()
    }

    override val layoutId = R.layout.fr_home
    override val viewModelClass = HomeVM::class.java
    private var callback: HomeCB? = null

    private val snackTextObserver = Observer<String> { showSnackbar(it) }
    private val beanObserver = Observer<BeanModel> { bean -> bean?.let { initView(it) } }
    private val latitudeObserver = Observer<String> { PrefProvider.latitude = it }
    private val longitudeObserver = Observer<String> {
        PrefProvider.longitude = it
        vm.getWeather()
    }

    override fun observeLiveData() {
        vm.run {
            snackTextLD.observe(this@HomeFR, snackTextObserver)
            latLD.observe(this@HomeFR, latitudeObserver)
            longLD.observe(this@HomeFR, longitudeObserver)
            bean.observe(this@HomeFR, beanObserver)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners(btnHomeGo)
        if (PrefProvider.latitude != DOUBLE_ZERO.toString()) vm.getWeather()
        permissionsBuilder(Manifest.permission.ACCESS_FINE_LOCATION).build().send {
            if (it.allGranted()) currentLocation()
        }
    }

    private fun initView(bean: BeanModel) {
        LOG.v(message = "${this.javaClass.simpleName}|${object {}.javaClass.enclosingMethod?.name}| ")
        clHomeLocalWeather.show()
        txtHomeCity.text = bean.city
        val temp = bean.list?.get(0)?.temp?.let { AppUtil.getTemp(it.toString()) }
        val tempMin = bean.list?.get(0)?.tempMin?.let { AppUtil.getTemp(it.toString()) }
        val tempMax = bean.list?.get(0)?.tempMax?.let { AppUtil.getTemp(it.toString()) }
        txtHomeTemp.text = temp?.let { "$it°C" }
        txtHomeSpeed.text = "wind speed ${bean.list?.get(0)?.windSpeed} m/sec"
        txtHomeMin.text = "min $tempMin°C"
        txtHomeMax.text = "max $tempMax°C"
        imgHomeIcon.loadImage(
            AppUtil.getIcon(
                bean.list?.get(0)?.weather?.get(0)?.icon ?: EMPTY_STRING
            )
        )
        bean.list?.let {
            rvHome.apply {
                adapter = HomeAdapter(it)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            btnHomeGo -> if (etHome.trimText().isNotEmpty()) callback?.openCities(etHome.trimText())
        }
    }

    //get current latitude and longitude
    private fun currentLocation() {
        LOG.v(message = "${this.javaClass.simpleName}|${object {}.javaClass.enclosingMethod?.name}| ")
        if (ContextCompat.checkSelfPermission(
                App.instance.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_DENIED
        ) {
            val locationManager =
                activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.allProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0,
                    1000f,
                    object : LocationListener {
                        override fun onLocationChanged(location: Location?) {
                            LOG.v(message = "${this.javaClass.simpleName}|${object {}.javaClass.enclosingMethod?.name}| lat - ${location?.latitude}, long - ${location?.longitude}")
                            vm.apply {
                                latLD.value = (location?.latitude ?: DOUBLE_ZERO).toString()
                                longLD.value = (location?.longitude ?: DOUBLE_ZERO).toString()
                            }
                        }

                        override fun onStatusChanged(p: String?, s: Int, e: Bundle?) {}
                        override fun onProviderEnabled(provider: String?) {}
                        override fun onProviderDisabled(provider: String?) {}
                    })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        etHome.setText(EMPTY_STRING)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = bindInterfaceOrThrow<HomeCB>(parentFragment, context)
    }

    override fun onDetach() {
        callback = null
        super.onDetach()
    }


}