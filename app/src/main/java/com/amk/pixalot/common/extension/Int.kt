package com.amk.pixalot.common.extension

import android.util.DisplayMetrics

fun Int.toDp(displayMetrics: DisplayMetrics) = toFloat().toDp(displayMetrics)
