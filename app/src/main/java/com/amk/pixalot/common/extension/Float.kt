package com.amk.pixalot.common.extension

import android.util.DisplayMetrics
import android.util.TypedValue

fun Float.toDp(displayMetrics: DisplayMetrics) =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, displayMetrics)