package com.apps.kim.weather.tools.extensions

import android.os.SystemClock
import android.view.View
import android.view.View.*
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.apps.kim.weather.app.App
import com.apps.kim.weather.app.DEFAULT_DEBOUNCE_TIME

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
fun View.OnClickListener.setClickListeners(vararg views: View) {
    views.forEach { view -> view.setOnClickListener(this) }
}

fun View.setDrawable(@DrawableRes drawableRes: Int) {
    background = App.instance.getDrawable(drawableRes)
}

fun View.hide(gone: Boolean = true) {
    visibility = if (gone) GONE else INVISIBLE
}

fun View.show() {
    visibility = VISIBLE
}

fun View.setBackgroundColorCompact(@ColorRes colorRes: Int) {
    setBackgroundColor(ContextCompat.getColor(App.instance, colorRes))
}

fun View.OnClickListener.setClickListenerWithDebounce(
    vararg views: View,
    debounceTime: Long = DEFAULT_DEBOUNCE_TIME
) {
    val clickListener = object : View.OnClickListener {
        private var lastClickTime = 0L
        override fun onClick(view: View) {
            SystemClock.elapsedRealtime()
                .takeIf {
                    it - lastClickTime > debounceTime
                }
                ?.run {
                    this@setClickListenerWithDebounce.onClick(view)
                    lastClickTime = this
                }
        }
    }
    views.forEach { view -> view.setOnClickListener(clickListener) }
}

fun View.debounceClick(debounceTime: Long = 1000L, action: () -> Unit) {
    setOnClickListener {
        when {
            tag != null && (tag as Long) > System.currentTimeMillis() -> return@setOnClickListener
            else -> {
                tag = System.currentTimeMillis() + debounceTime
                action()
            }
        }
    }
}