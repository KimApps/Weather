package com.apps.kim.weather.tools.utils

import android.util.Log
import com.apps.kim.weather.BuildConfig
import com.apps.kim.weather.app.EMPTY_STRING

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
object LOG {

    private const val DEFAULT_TAG = "KIM_LOG ---> "
    private const val DEFAULT_MESSAGE = EMPTY_STRING

    fun e(
        tag: String = DEFAULT_TAG,
        message: String? = DEFAULT_MESSAGE,
        throwable: Throwable? = null
    ) {
        if (isDebug) {
            throwable?.let {
                message?.let {
                    Log.e(tag, message, throwable)
                } ?: Log.e(tag, throwable.message, throwable)
            } ?: message?.let {
                Log.e(tag, message)
            }
        }
    }

    fun d(
        tag: String = DEFAULT_TAG,
        message: String? = DEFAULT_MESSAGE,
        throwable: Throwable? = null
    ) {
        if (isDebug) {
            throwable?.let {
                message?.let {
                    Log.d(tag, message, throwable)
                } ?: Log.d(tag, throwable.message, throwable)
            } ?: message?.let {
                Log.d(tag, message)
            }
        }
    }

    fun w(
        tag: String = DEFAULT_TAG,
        message: String? = DEFAULT_MESSAGE,
        throwable: Throwable? = null
    ) {
        if (isDebug) {
            throwable?.let {
                message?.let {
                    Log.w(tag, message, throwable)
                } ?: Log.w(tag, throwable.message, throwable)
            } ?: message?.let {
                Log.w(tag, message)
            }
        }
    }

    fun i(
        tag: String = DEFAULT_TAG,
        message: String? = DEFAULT_MESSAGE,
        throwable: Throwable? = null
    ) {
        if (isDebug) {
            throwable?.let {
                message?.let {
                    Log.i(tag, message, throwable)
                } ?: Log.i(tag, throwable.message, throwable)
            } ?: message?.let {
                Log.i(tag, message)
            }
        }
    }

    fun v(
        tag: String = DEFAULT_TAG,
        message: String? = DEFAULT_MESSAGE,
        throwable: Throwable? = null
    ) {
        if (isDebug) {
            throwable?.let {
                message?.let {
                    Log.v(tag, message, throwable)
                } ?: Log.v(tag, throwable.message, throwable)
            } ?: message?.let {
                Log.v(tag, message)
            }
        }
    }

    private val isDebug: Boolean
        get() = BuildConfig.DEBUG

}