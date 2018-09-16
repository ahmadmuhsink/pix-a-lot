package com.amk.pixalot.common.ui.photolist

import android.support.v7.util.DiffUtil
import com.amk.pixalot.domain.viewmodel.ImageItem

class PhotoDiffUtil(
        private val oldPhotos: List<ImageItem>,
        private val newPhotos: List<ImageItem>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldPhotos.size

    override fun getNewListSize() = newPhotos.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldPhotos[oldItemPosition] == newPhotos[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldPhotos[oldItemPosition].previewUrl == newPhotos[newItemPosition].previewUrl &&
                    oldPhotos[oldItemPosition].fullscreenUrl == newPhotos[newItemPosition].fullscreenUrl
}
