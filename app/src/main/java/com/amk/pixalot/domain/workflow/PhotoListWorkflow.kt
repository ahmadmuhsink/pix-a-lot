package com.amk.pixalot.domain.workflow

import com.amk.pixalot.domain.repo.PexelRepository
import com.amk.pixalot.domain.repo.PixabayRepository
import com.amk.pixalot.domain.repo.UnsplashRepository
import com.amk.pixalot.domain.store.PhotoListStore
import com.amk.pixalot.domain.viewmodel.ImageItem
import com.amk.pixalot.screen.home.photolist.PhotoListViewModel
import io.reactivex.Observable
import io.reactivex.functions.Function3

open class PhotoListWorkflow(
        private val pixabayRepository: PixabayRepository,
        private val unsplashRepository: UnsplashRepository,
        private val pexelRepository: PexelRepository,
        private val store: PhotoListStore
) {

    private val zipper by lazy {
        Function3<List<ImageItem>, List<ImageItem>, List<ImageItem>, List<ImageItem>> { pixabay, unsplash, pexel ->
            pixabay + unsplash + pexel
        }
    }

    fun load(): Observable<PhotoListViewModel> {
        return if (store.currentImages.isNotEmpty())
            Observable.just(store.deriveViewModel())
        else
            Observable.zip(
                    fetchImages { pixabayRepository.fetchImages(1) },
                    fetchImages { unsplashRepository.fetchImages(1) },
                    fetchImages { pexelRepository.fetchImages(1) },
                    zipper
            ).map { store.update(it) }
    }

    fun loadMore(): Observable<PhotoListViewModel> {
        return Observable.zip(
                fetchImages { pixabayRepository.loadMore() },
                fetchImages { unsplashRepository.loadMore() },
                fetchImages { pexelRepository.loadMore() },
                zipper
        ).map { store.add(it) }
    }

    private fun fetchImages(
            fetch: () -> Observable<List<ImageItem>>
    ): Observable<List<ImageItem>> = fetch()
            .onErrorResumeNext(Observable.just(listOf()))
}