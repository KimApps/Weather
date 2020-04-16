package com.apps.kim.weather.tools.utils

import android.content.Context
import com.apps.kim.weather.app.App
import com.apps.kim.weather.app.LoginActivity

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
object LogoutUtils {
    fun doLogout(context: Context = App.instance) {
        PrefProvider.clearSession()
        LoginActivity.start(context)
    }
}