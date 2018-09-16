package com.amk.pixalot.domain.repo

import com.amk.pixalot.domain.viewmodel.ImageItem
import io.reactivex.Observable

interface ImageSourceRepo {
    fun fetchImages(page: Int = 1): Observable<List<ImageItem>>
    fun loadMore(): Observable<List<ImageItem>>
}