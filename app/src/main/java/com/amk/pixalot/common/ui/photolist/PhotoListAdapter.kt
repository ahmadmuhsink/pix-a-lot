package com.amk.pixalot.common.ui.photolist

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.amk.pixalot.arch.UserAction
import com.amk.pixalot.common.UserActionCallback
import com.amk.pixalot.common.ui.PhotoPreviewView
import com.amk.pixalot.domain.viewmodel.ImageItem
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class PhotoListAdapter(
        private var photos: ArrayList<ImageItem>
) : RecyclerView.Adapter<PhotoListViewHolder>() {

    private var cardClickListener: UserActionCallback? = null

    private fun invokeAction(userAction: UserAction) {
        cardClickListener?.invoke(userAction)
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): PhotoListViewHolder = PhotoListViewHolder(PhotoPreviewView(parent.context))

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        val item = photos[position]
        holder.s?.dispose()
        holder.s = (holder.view as PhotoPreviewView).bind(item)
                .subscribe(::invokeAction)
    }

    fun update(newPhotos: List<ImageItem>): Observable<UserAction> {
        val oldPhotos = photos
        photos = ArrayList(newPhotos)
        DiffUtil.calculateDiff(PhotoDiffUtil(oldPhotos, photos)).dispatchUpdatesTo(this)
        return bind()
    }

    private fun bind(): Observable<UserAction> = Observable.create { emitter ->
        cardClickListener = {
            emitter.onNext(it)
        }
    }
}

class PhotoListViewHolder(
        val view: View,
        var s: Disposable? = null
) : RecyclerView.ViewHolder(view)
