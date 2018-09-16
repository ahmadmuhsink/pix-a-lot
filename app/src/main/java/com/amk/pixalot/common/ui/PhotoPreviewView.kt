package com.amk.pixalot.common.ui

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import com.amk.pixalot.R
import com.amk.pixalot.arch.UserAction
import com.amk.pixalot.common.extension.displayMetrics
import com.amk.pixalot.common.extension.load
import com.amk.pixalot.common.extension.rxClick
import com.amk.pixalot.common.extension.setPaddingInDp
import com.amk.pixalot.common.extension.toDp
import com.amk.pixalot.domain.viewmodel.ImageItem
import io.reactivex.Observable
import kotlinx.android.synthetic.main.layout_photo_preview.view.imgPreview

class PhotoPreviewView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val circularDrawable by lazy { PixalotCircularProgress(context) }

    init {
        setPaddingInDp(32)
        cardElevation = 8.toDp(displayMetrics)
        radius = 8.toDp(displayMetrics)
        useCompatPadding = true
        preventCornerOverlap = false
        isClickable = true
        View.inflate(context, R.layout.layout_photo_preview, this)
    }

    fun bind(item: ImageItem): Observable<UserAction> {
        imgPreview.load(
                item.previewUrl,
                circularDrawable,
                circularDrawable
        )

        return this.rxClick().map { PhotoPreviewAction.ActionFullImage(item.fullscreenUrl) }
    }
}

sealed class PhotoPreviewAction : UserAction {
    data class ActionFullImage(val fullscreenUrl: String) : PhotoPreviewAction()
}