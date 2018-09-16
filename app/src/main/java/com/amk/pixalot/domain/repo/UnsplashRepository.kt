package com.amk.pixalot.domain.repo

import com.amk.pixalot.domain.network.unsplash.UnsplashApi
import com.amk.pixalot.domain.viewmodel.ImageItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io

open class UnsplashRepository(private val unsplashApi: UnsplashApi) : ImageSourceRepo {

    private var currentPage: Int = 1

    override fun fetchImages(page: Int): Observable<List<ImageItem>> = unsplashApi
            .getImages(page = page).map { images ->
                images.images.asSequence()
                        .filterNot { item ->
                            item.previewUrl.isNullOrBlank() || item.fullscreenUrl.isNullOrBlank()
                        }
                        .map { item ->
                            ImageItem(item.urls.previewUrl.orEmpty(),
                                    item.urls.fullscreenUrl.orEmpty())
                        }.toList()
            }
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun loadMore(): Observable<List<ImageItem>> {
        return fetchImages(currentPage.inc()).doOnNext { currentPage = currentPage.inc() }
    }
}