package com.polly.myapplication

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CustomLayoutManager3 : androidx.recyclerview.widget.RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): androidx.recyclerview.widget.RecyclerView.LayoutParams {
        return androidx.recyclerview.widget.RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: androidx.recyclerview.widget.RecyclerView.Recycler, state: androidx.recyclerview.widget.RecyclerView.State) {
        val viewWidth = 300
        for (i in 0..3) {
            val left = i * viewWidth
            val right = left + viewWidth
            val top = 0
            val bottom = top + viewWidth

            val view = recycler.getViewForPosition(i)
            addView(view)

            measureChildWithMargins(view, viewWidth, viewWidth)

            layoutDecoratedWithMargins(view, left, top, right, bottom)
        }
    }
}