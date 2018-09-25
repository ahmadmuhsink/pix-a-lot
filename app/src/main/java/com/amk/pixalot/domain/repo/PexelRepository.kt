package com.amk.pixalot.domain.repo

import com.amk.pixalot.domain.network.pexel.PexelApi
import com.amk.pixalot.domain.viewmodel.ImageItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io

open class PexelRepository(private val pexelApi: PexelApi) : BaseImageRepo() {

    override fun fetchImages(page: Int): Observable<List<ImageItem>> = pexelApi
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
}