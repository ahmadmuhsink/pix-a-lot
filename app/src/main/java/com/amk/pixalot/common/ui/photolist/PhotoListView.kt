package com.amk.pixalot.common.ui.photolist

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.amk.pixalot.arch.UserAction
import com.amk.pixalot.common.AppConfig.COLUMN_COUNT
import com.amk.pixalot.common.extension.displayMetrics
import com.amk.pixalot.common.extension.toDp
import com.amk.pixalot.common.ui.GridSpacingDecorator
import com.amk.pixalot.domain.viewmodel.ImageItem
import com.amk.pixalot.screen.home.photolist.PhotoListAction
import com.jakewharton.rxbinding2.support.v7.widget.scrollStateChanges
import io.reactivex.Observable

class PhotoListView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        layoutManager = GridLayoutManager(context, COLUMN_COUNT)
        addItemDecoration(GridSpacingDecorator(COLUMN_COUNT, 16.toDp(displayMetrics).toInt()))
        adapter = PhotoListAdapter(arrayListOf())
        layoutParams = generateDefaultLayoutParams()
        (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
    }

    fun bind(images: List<ImageItem>): Observable<UserAction> {
        return Observable.merge(
                (adapter as PhotoListAdapter).update(images),
                this.scrollStateChanges()
                        .filter { this.canScrollVertically(1).not() }
                        .map { PhotoListAction.ActionLoadMore }
        )
    }
}
