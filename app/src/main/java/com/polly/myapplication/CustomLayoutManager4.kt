package com.polly.myapplication

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class CustomLayoutManager4(val screenWidth: Int, val viewWidth: Int) : RecyclerView.LayoutManager() {

    private var horizontalScrollOffset: Int = 0

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        horizontalScrollOffset += dx
        fill(recycler, state)
        return dx
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        fill(recycler, state)
    }

    private fun fill(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        var firstVisiblePosition = horizontalScrollOffset / viewWidth
        var lastVisiblePosition = (horizontalScrollOffset + screenWidth) / viewWidth
        if (firstVisiblePosition < 0) {
            firstVisiblePosition = 0
        }
        if (lastVisiblePosition >= itemCount) {
            lastVisiblePosition = itemCount -1
        }

        for (i in firstVisiblePosition..lastVisiblePosition) {
            val left = i * viewWidth - horizontalScrollOffset
            val right = left + viewWidth
            val top = 0
            val bottom = top + viewWidth

            val view = recycler.getViewForPosition(i)
            addView(view)

            measureChildWithMargins(view, viewWidth, viewWidth)

            layoutDecoratedWithMargins(view, left, top, right, bottom)
        }
        val scrapListCopy = recycler.scrapList.toList()
        scrapListCopy.forEach {
            recycler.recycleView(it.itemView)
        }
    }
}