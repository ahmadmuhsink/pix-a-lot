package com.amk.pixalot.common.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.widget.CircularProgressDrawable
import com.amk.pixalot.R

class PixalotCircularProgress(context: Context) : CircularProgressDrawable(context) {
    init {
        setColorSchemeColors(ContextCompat.getColor(context, R.color.brickRed))
        setStyle(LARGE)
        start()
    }
}