package com.amk.pixalot.common.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.amk.pixalot.R
import com.amk.pixalot.common.extension.load
import kotlinx.android.synthetic.main.layout_full_photo.view.imgHighResPhoto

class HighResPhotoView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val circularDrawable by lazy { PixalotCircularProgress(context) }

    init {
        setBackgroundColor(ContextCompat.getColor(context, R.color.gainsboro))
        layoutParams = generateDefaultLayoutParams()
        View.inflate(context, R.layout.layout_full_photo, this)
    }

    fun load(url: String) {
        imgHighResPhoto.load(
                url,
                circularDrawable,
                circularDrawable
        )
    }
}