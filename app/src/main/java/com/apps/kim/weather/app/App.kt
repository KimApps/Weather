package com.apps.kim.weather.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.multidex.MultiDex
import com.apps.kim.weather.BuildConfig
import com.apps.kim.weather.tools.utils.LogoutUtils
import com.securepreferences.SecurePreferences

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
class App : Application() {
    companion object {
        lateinit var instance: App
            private set
        lateinit var prefs: SharedPreferences

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = if (BuildConfig.DEBUG) getSharedPreferences()
        else getSecurePreferences()
    }

    private fun getSecurePreferences() =
        SecurePreferences(this, BuildConfig.SECURE_PREF_PASSWORD,
            APP_PREFERENCES
        )

    private fun getSharedPreferences() =
        instance.applicationContext.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    fun toast(resId: Int) = Toast.makeText(this, getStringApp(resId), Toast.LENGTH_LONG).show()
    fun getStringApp(@StringRes stringId: Int) = instance.getString(stringId)
    fun getDrawableApp(drawableId: Int) = instance.getDrawable(drawableId)
    fun onLogout() = LogoutUtils.doLogout()

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}