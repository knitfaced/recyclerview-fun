package com.polly.myapplication

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT

class CustomLayoutManager2 : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        val view = recycler.getViewForPosition(0)
        addView(view)

        val viewWidth = 300

        val left = 0
        val right = left + viewWidth
        val top = 0
        val bottom = top + viewWidth

        measureChild(view, viewWidth, viewWidth)

        layoutDecorated(view, left, top, right, bottom)
    }
}