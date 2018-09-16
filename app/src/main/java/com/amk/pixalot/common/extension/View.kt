package com.amk.pixalot.common.extension

import android.util.DisplayMetrics
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

val View.displayMetrics: DisplayMetrics
    get() {
        val displayMetrics = DisplayMetrics()
        context.asActivity()?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics
    }

fun View.setPaddingInDp(padding: Int) {
    val p = padding.toDp(displayMetrics).toInt()
    setPadding(p, p, p, p)
}

fun View.rxClick(): Observable<Any> = RxView.clicks(this)
        .debounce(200, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
