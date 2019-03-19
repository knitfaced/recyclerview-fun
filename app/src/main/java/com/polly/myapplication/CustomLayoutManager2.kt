package com.polly.myapplication

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT

class CustomLayoutManager2 : androidx.recyclerview.widget.RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): androidx.recyclerview.widget.RecyclerView.LayoutParams {
        return androidx.recyclerview.widget.RecyclerView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: androidx.recyclerview.widget.RecyclerView.Recycler, state: androidx.recyclerview.widget.RecyclerView.State) {
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