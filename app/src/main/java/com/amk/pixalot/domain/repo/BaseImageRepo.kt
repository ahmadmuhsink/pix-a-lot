package com.amk.pixalot.domain.repo

import com.amk.pixalot.domain.viewmodel.ImageItem
import io.reactivex.Observable

abstract class BaseImageRepo {

    private var currentPage: Int = 1

    abstract fun fetchImages(page: Int = 1): Observable<List<ImageItem>>

    fun loadMore(): Observable<List<ImageItem>> =
            fetchImages(currentPage.inc()).doOnNext { currentPage = currentPage.inc() }
}