package com.apps.kim.weather.app

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.apps.kim.weather.R
import com.apps.kim.weather.fragments.cities.CitiesCB
import com.apps.kim.weather.fragments.cities.CitiesFR
import com.apps.kim.weather.fragments.home.HomeCB
import com.apps.kim.weather.fragments.home.HomeFR
import com.apps.kim.weather.tools.base.BaseLifecycleActivity
import com.apps.kim.weather.tools.utils.LOG
import com.apps.kim.weather.tools.utils.PrefProvider
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send

class MainActivity : BaseLifecycleActivity<MainVM>(), HomeCB, CitiesCB {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }

    override val layoutId = R.layout.activity_main
    override val containerId = R.id.mContainer
    override val viewModelClass = MainVM::class.java
    override fun observeLiveData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFR(HomeFR.newInstance(), false)
    }

    override fun openCities(cities: String) = replaceFR(CitiesFR.newInstance(cities))
}
