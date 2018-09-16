package com.amk.pixalot.screen.home.photolist

import com.amk.pixalot.arch.BasePresenter
import com.amk.pixalot.arch.BaseView
import com.amk.pixalot.domain.viewmodel.ImageItem

class PhotoListContract {

    interface View : BaseView<PhotoListViewModel> {
        fun openFullScreenView(url: String)
    }

    abstract class Presenter : BasePresenter<PhotoListViewModel, View>() {
        abstract fun load()
    }
}

data class PhotoListViewModel(val images: List<ImageItem>)