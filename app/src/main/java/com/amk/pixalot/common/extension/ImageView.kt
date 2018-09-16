package com.amk.pixalot.common.extension

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

private fun createRequestOptions(
        placeholder: Drawable,
        error: Drawable
): RequestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .also { r -> r.placeholder(placeholder) }
        .also { r -> r.error(error) }

fun ImageView.load(
        url: String,
        placeholder: Drawable,
        error: Drawable
) {
    val options = createRequestOptions(placeholder, error)
    Glide.with(context).load(url).apply(options).into(this)
}
