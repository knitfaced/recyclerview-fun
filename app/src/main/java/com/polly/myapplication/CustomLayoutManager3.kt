package com.polly.myapplication

import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView.*

class CustomLayoutManager3 : LayoutManager() {

    override fun generateDefaultLayoutParams(): LayoutParams
            = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

    override fun onLayoutChildren(recycler: Recycler, state: State) {
        val viewWidth = 300
        for (i in 0..3) {
            val left = i * viewWidth
            val right = left + viewWidth
            val top = 0
            val bottom = top + viewWidth

            val view = recycler.getViewForPosition(i)
            addView(view)

            measureChild(view, viewWidth, viewWidth)

            layoutDecorated(view, left, top, right, bottom)
        }
    }
}