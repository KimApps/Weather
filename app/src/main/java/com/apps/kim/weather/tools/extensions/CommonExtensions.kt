package com.apps.kim.weather.tools.extensions

import android.view.View
import android.view.ViewGroup
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    block: (T1, T2, T3) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    block: (T1, T2, T3, T4) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null) block(p1, p2, p3, p4) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    p5: T5?,
    block: (T1, T2, T3, T4, T5) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null) block(
        p1,
        p2,
        p3,
        p4,
        p5
    ) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    p5: T5?,
    p6: T6?,
    block: (T1, T2, T3, T4, T5, T6) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null && p6 != null) block(
        p1,
        p2,
        p3,
        p4,
        p5,
        p6
    ) else null
}

inline fun <T, R> withNotNull(receiver: T?, block: T.() -> R): R? = receiver?.block()

fun View.setMargins(
    left: Int = 0,
    top: Int = 0,
    right: Int = 0,
    bottom: Int = 0
) {
    layoutParams = (layoutParams as? ViewGroup.MarginLayoutParams)
        ?.apply { setMargins(left, top, right, bottom) } ?: return
}

fun String.toHash(): String {
    //HASH_TYPE can be: "SHA-256","SHA-512","SHA-1","MD5"
    val HASH_TYPE = "SHA-256"
    val bytes = MessageDigest
        .getInstance(HASH_TYPE)
    return BigInteger(1, bytes.digest(("$this~it.reat.Mobile.Team#2019!").toByteArray())).toString(
        16
    ).padStart(32, '0')
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}
