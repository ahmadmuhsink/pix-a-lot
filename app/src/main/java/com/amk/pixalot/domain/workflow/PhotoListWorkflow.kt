package com.amk.pixalot.domain.workflow

import com.amk.pixalot.domain.repo.ImageSourceRepo
import com.amk.pixalot.domain.store.PhotoListStore
import com.amk.pixalot.domain.viewmodel.ImageItem
import com.amk.pixalot.screen.home.photolist.PhotoListViewModel
import io.reactivex.Observable

open class PhotoListWorkflow(
        private val repos: List<ImageSourceRepo>,
        private val store: PhotoListStore
) {
    private fun zipper(data: Array<Any>): List<ImageItem> =
            data.map { it as List<ImageItem> }.flatten()

    fun load(): Observable<PhotoListViewModel> {
        return if (store.currentImages.isNotEmpty())
            Observable.just(store.deriveViewModel())
        else
            fetchAll({ fetchImages(1) }, { update(it) })
    }

    fun loadMore(): Observable<PhotoListViewModel> = fetchAll({ loadMore() }, { add(it) })

    private inline fun fetchAll(
            fetch: ImageSourceRepo.() -> Observable<List<ImageItem>>,
            crossinline storeOperation: PhotoListStore.(List<ImageItem>) -> PhotoListViewModel
    ): Observable<PhotoListViewModel> {
        return Observable
                .zip(repos.map { fetch(it).onErrorResumeNext(Observable.just(listOf())) }, ::zipper)
                .map { storeOperation(store, it) }
    }
}