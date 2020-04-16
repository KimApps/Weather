package com.apps.kim.weather.tools.utils

import android.annotation.SuppressLint
import com.apps.kim.weather.app.*

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
@SuppressLint("CommitPrefEdits", "ApplySharedPref")
object PrefProvider {

    private val preferences = App.prefs

    fun saveSession(userId: Int, token: String) {
        preferences.edit()
            .putString(PREF_TOKEN, "$PREFIX $token")
            .putInt(PREF_USER_ID, userId)
            .putBoolean(PREF_FIRST_OPEN, false)
            .commit()
    }

    fun clearSession() = preferences.edit().clear().commit()

    var isFirstOpen: Boolean
        get() = preferences.getBoolean(PREF_FIRST_OPEN, true)
        set(value) {
            preferences.edit()
                .putBoolean(PREF_FIRST_OPEN, value)
                .commit()
        }

    var token: String
        get() = preferences.getString(
            PREF_TOKEN,
            EMPTY_STRING
        ) ?: EMPTY_STRING
        set(value) {
            preferences.edit()
                .putString(PREF_TOKEN, value)
                .commit()
        }

    var userId: Int
        get() = preferences.getInt(
            PREF_USER_ID,
            EMPTY_INT
        )
        set(value) {
            preferences.edit()
                .putInt(PREF_USER_ID, value)
                .commit()
        }
    var latitude: String
        get() = preferences.getString(PREF_LAT, EMPTY_STRING) ?: DOUBLE_ZERO.toString()
        set(value) {
            preferences.edit()
                .putString(PREF_LAT, value)
                .commit()
        }

    var longitude: String
        get() = preferences.getString(PREF_LONG, EMPTY_STRING) ?: DOUBLE_ZERO.toString()
        set(value) {
            preferences.edit()
                .putString(PREF_LONG, value)
                .commit()
        }
}