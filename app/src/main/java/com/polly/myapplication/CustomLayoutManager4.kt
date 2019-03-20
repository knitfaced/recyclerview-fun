package com.polly.myapplication

import android.content.res.Resources
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView.*

class CustomLayoutManager4(resources: Resources, private val screenWidth: Int) : LayoutManager() {

    private var horizontalScrollOffset: Int = 0
    private val viewWidth = resources.getDimensionPixelSize(R.dimen.item_width)

    override fun generateDefaultLayoutParams(): LayoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

    override fun canScrollHorizontally(): Boolean = true

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler, state: State): Int {
        horizontalScrollOffset += dx
        fill(recycler, state)
        return dx
    }

    override fun onLayoutChildren(recycler: Recycler, state: State) {
        fill(recycler, state)
    }

    private fun fill(recycler: Recycler, state: State) {
        detachAndScrapAttachedViews(recycler)

        var firstVisiblePosition = horizontalScrollOffset / viewWidth
        var lastVisiblePosition = (horizontalScrollOffset + screenWidth) / viewWidth

        firstVisiblePosition = if (firstVisiblePosition < 0) 0 else firstVisiblePosition
        lastVisiblePosition = if (lastVisiblePosition >= itemCount) itemCount-1 else lastVisiblePosition

        for (i in firstVisiblePosition..lastVisiblePosition) {
            val left = i * viewWidth - horizontalScrollOffset
            val right = left + viewWidth
            val top = 0
            val bottom = top + viewWidth

            val view = recycler.getViewForPosition(i)
            addView(view)

            measureChild(view, viewWidth, viewWidth)

            layoutDecorated(view, left, top, right, bottom)
        }
        val scrapListCopy = recycler.scrapList.toList()
        scrapListCopy.forEach {
            recycler.recycleView(it.itemView)
        }
    }
}