package com.apps.kim.weather.tools.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
internal val NO_FLAGS = 0

fun Activity.hideKeyboard() = currentFocus?.let {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(it.windowToken, NO_FLAGS)
    }
}

fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun androidx.fragment.app.Fragment.hideKeyboard() = activity?.hideKeyboard()


