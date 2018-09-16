package com.amk.pixalot.common.ui

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class GridSpacingDecorator(private val column: Int, spacing: Int) : RecyclerView.ItemDecoration() {
    private val quarterSpacing = spacing / 4
    private val halfSpacing = spacing / 2
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (column <= 0) return

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        when {
            position % column == 0 -> outRect.set(halfSpacing, quarterSpacing, quarterSpacing,
                    quarterSpacing)
            (position + 1) % column == 0 -> outRect.set(quarterSpacing, quarterSpacing, halfSpacing,
                    quarterSpacing)
            else -> outRect.set(quarterSpacing, quarterSpacing, quarterSpacing, quarterSpacing)
        }
    }
}