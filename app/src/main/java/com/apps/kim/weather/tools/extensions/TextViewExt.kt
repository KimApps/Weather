package com.apps.kim.weather.tools.extensions

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import android.widget.TextView
import com.apps.kim.weather.app.App

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
fun TextView.setTextColorCompat(@ColorRes colorRes: Int) {
    setTextColor(ContextCompat.getColor(App.instance, colorRes))
}

fun TextView.setBackgroundColorCompat(@ColorRes colorRes: Int) {
    setBackgroundColor(ContextCompat.getColor(App.instance, colorRes))
}

fun TextView.getStringText() = this.text.toString()

fun TextView.setTextOrHide(string: String?) {
    this.text = string?.apply {
        if (string.isEmpty()) this@setTextOrHide.hide() else this@setTextOrHide.show()
    }
}
