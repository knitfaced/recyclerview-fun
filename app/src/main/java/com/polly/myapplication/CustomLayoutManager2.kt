package com.polly.myapplication

import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView.*

class CustomLayoutManager2 : LayoutManager() {

    override fun generateDefaultLayoutParams(): LayoutParams
            = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

    override fun onLayoutChildren(recycler: Recycler, state: State) {
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