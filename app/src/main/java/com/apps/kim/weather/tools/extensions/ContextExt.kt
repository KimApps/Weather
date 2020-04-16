package com.apps.kim.weather.tools.extensions

import android.content.Context
import androidx.core.content.ContextCompat

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
fun Context.getInteger(intRes: Int) = this.resources.getInteger(intRes)

fun Context.resColor(colorRes: Int) = ContextCompat.getColor(this, colorRes)
