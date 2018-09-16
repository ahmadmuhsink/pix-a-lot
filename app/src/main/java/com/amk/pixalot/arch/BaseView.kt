package com.amk.pixalot.arch

import android.arch.lifecycle.LifecycleObserver
import android.support.annotation.StringRes
import io.reactivex.Observable

interface BaseView<in M> : LifecycleObserver {
    fun showData(model: M): Observable<out UserAction>
    fun actions(): Observable<out UserAction> = Observable.never()
    fun showLoading() {}
    fun showError(@StringRes message: Int) {}
}

interface UserAction
