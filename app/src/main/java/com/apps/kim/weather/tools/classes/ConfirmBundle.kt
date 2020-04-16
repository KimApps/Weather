package com.apps.kim.weather.tools.classes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
@Parcelize
data class ConfirmBundle(
    val text: String?,
    val positiveText: String?,
    val negativeText: String?,
    val resId: Int?,
    val isCancel: Boolean?
) : Parcelable