package com.amk.pixalot.arch

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<in M, V : BaseView<M>> {
    private var s = CompositeDisposable()
    private var view: V? = null

    fun attach(v: V) {
        this.view = v
        onAttach()
    }

    fun detach() {
        s.clear()
        onDetach()
        this.view = null
    }

    fun view(): V? = view

    protected fun observe(execute: () -> Disposable) {
        s.add(execute())
    }

    protected open fun onAttach() {}
    protected open fun onDetach() {}
}