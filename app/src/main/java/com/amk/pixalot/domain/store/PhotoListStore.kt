package com.amk.pixalot.domain.store

import com.amk.pixalot.domain.viewmodel.ImageItem
import com.amk.pixalot.screen.home.photolist.PhotoListViewModel

class PhotoListStore {

    data class State(
            val images: ArrayList<ImageItem> = arrayListOf()
    )

    private var state = State()

    val currentImages: List<ImageItem>
        get() = state.images

    fun update(images: List<ImageItem>): PhotoListViewModel {
        state = state.copy(images = ArrayList(images))
        return deriveViewModel()
    }

    fun add(images: List<ImageItem>): PhotoListViewModel {
        state.images.addAll(images)
        return deriveViewModel()
    }

    fun deriveViewModel(): PhotoListViewModel {
        return PhotoListViewModel(state.images)
    }
}