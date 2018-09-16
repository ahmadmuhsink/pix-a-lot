package com.amk.pixalot.common.extension

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v7.content.res.AppCompatResources
import android.support.v7.view.ContextThemeWrapper

fun Context?.asActivity(): Activity? {
    return when {
        this == null -> return null
        this is Activity -> return this
        this is ContextWrapper -> this.baseContext.asActivity()
        this is ContextThemeWrapper -> this.baseContext.asActivity()
        else -> null
    }
}
