package com.apps.kim.weather.tools.extensions

import androidx.annotation.ColorRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.apps.kim.weather.app.App

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */

fun CardView.setCardColor(@ColorRes colorRes: Int) {
    setCardBackgroundColor(ContextCompat.getColor(App.instance, colorRes))
}
